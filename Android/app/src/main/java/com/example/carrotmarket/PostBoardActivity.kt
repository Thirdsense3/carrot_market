package com.example.carrotmarket

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.dto.MyData
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostBoardActivity : AppCompatActivity() {
    val board : Board = Board(0,0,"","",0,0F,0F,"", "", "", 0, 0, 0, "null")
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_ALBUM = 2
    lateinit var curPhotoPath : String
    //lateinit var image : ImageView
    var images = ArrayList<MultipartBody.Part>()
    var imageFile : File? = null
    var filename : String = ""
    var cntImage : Int = 0
    var pictures = ""
    var bitmapImages = ArrayList<Bitmap>()
    var pictureList = ArrayList<String>()
    internal lateinit var sliderViewPager : ViewPager2

    var categoryData = arrayOf("1", "2", "3", "4", "5", "6")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_board)
        //image = findViewById<ImageView>(R.id.imageView)
        val categoryChoice = findViewById<Spinner>(R.id.categoryChoice)
        val postButton = findViewById<Button>(R.id.posting)
        val titleText = findViewById<EditText>(R.id.titleText)
        val textMessage = findViewById<EditText>(R.id.textMessage)
        val pictureButton = findViewById<Button>(R.id.picture)
        val albumButton = findViewById<Button>(R.id.album)
        val price = findViewById<EditText>(R.id.priceText)
        sliderViewPager = findViewById(R.id.viewpager2)
        //var tempbnt : Button = findViewById(R.id.tempbnt)


        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoryChoice.adapter = adapter
        var listener = SpinnerListener()

        val editIntent : Intent = getIntent()
        if(editIntent.hasExtra("id")) {
            val settingId : Long = editIntent.getLongExtra("id", 0L)
            board.id = settingId

            val settingRetrofit = RetrofitClient.getInstance()
            val settingApi = settingRetrofit.create(RetrofitService::class.java)

            settingApi.getBoard(settingId).enqueue(object : Callback<Board> {
                override fun onFailure(call: Call<Board>, t: Throwable) {
                    Toast.makeText(this@PostBoardActivity, "불러오기 실패", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Board>, response: Response<Board>) {
                    response.body()?.let {
                        titleText.setText(it.title)
                        price.setText(it.price.toString())
                        textMessage.setText(it.text)
                        pictures = it.picture

                        var existingImages = it.picture.split(' ')

                        for(pic in existingImages) {
                            if(pic.equals(""))
                                break
                            pictureList.add(pic)

                            val baseURL : String = "http://10.0.2.2:8080/"
                            val str: String = "${baseURL}/download/${board.id}/${pic}"

                            Glide.with(this@PostBoardActivity)
                                .asBitmap()
                                .load(str)
                                .into(object : CustomTarget<Bitmap>() {
                                    override fun onResourceReady(
                                        resource: Bitmap, transition: Transition<in Bitmap>?) {
                                        bitmapImages.add(resource)
                                        val existingFile : File = File.createTempFile("temp_", ".jpg")

                                        var out : FileOutputStream = FileOutputStream(existingFile)
                                        bitmapImages.last().compress(Bitmap.CompressFormat.JPEG, 100, out)
                                        out.close()

                                        var requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), existingFile)
                                        images.add(MultipartBody.Part.createFormData("imageFile", pic, requestBody))

                                        //image.setImageBitmap(bitmapImages.last())
                                        existingFile.deleteOnExit()

                                        sliderViewPager.offscreenPageLimit = 1
                                        var vAdapter = ViewPagerAdapter(this@PostBoardActivity, bitmapImages, images, pictureList)
                                        sliderViewPager.adapter = vAdapter

                                        sliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                            override fun onPageSelected(position : Int) {
                                                super.onPageSelected(position)
                                            }
                                        })
                                    }

                                    override fun onLoadCleared(placeholder: Drawable?) {
                                    }
                                })
                        }
                    }
                }
            })
        } else {
            sliderViewPager.offscreenPageLimit = 1
            var vAdapter = ViewPagerAdapter(this@PostBoardActivity, bitmapImages, images, pictureList)
            sliderViewPager.adapter = vAdapter

            sliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position : Int) {
                    super.onPageSelected(position)
                }
            })
        }

        pictureButton.setOnClickListener() {
            setPermission()
            takeCapture()
        }

        albumButton.setOnClickListener() {
            setPermission()
            openGalley()
        }

        postButton.setOnClickListener() {
            board.title = titleText.text.toString()
            board.text = textMessage.text.toString()
            board.price = price.text.toString().toLong()
            var temp = categoryChoice.selectedItem.toString()
            board.categoryId = temp.toInt()
            board.nickname = MyData.getMember().nickname
            board.picture = filename
            board.registerDate = SimpleDateFormat("yyyyMMdd").format(Date())
            board.deadlineDate = calculateDeadLine(board.registerDate, 5)
            board.locationX = MyData.getMember().locationX
            board.locationY = MyData.getMember().locationY
            pictures = ""
            for(t in pictureList)
                pictures += t + " "
            board.picture = pictures

            Log.d("deadline", board.deadlineDate)

            var retrofit = RetrofitClient.getInstance()
            var myApi = retrofit.create(RetrofitService::class.java)
            val intent = Intent(this, ListActivity::class.java)
            var tempId : Long

            myApi.boardPosting(board.id, board.price, board.title, board.text, board.categoryId, board.nickname, board.registerDate, board.deadlineDate!!, board.locationX, board.locationY, board.picture).enqueue(object : Callback<Board> {
                override fun onResponse(call : Call<Board>, response: Response<Board>) {
                    response.body()?.let {
                        Log.d("result", it.id.toString())
                        tempId = it.id

                        myApi.postPicture(tempId, images).enqueue(object : Callback<String> {
                            override fun onResponse(call: Call<String>, response: Response<String>) {
                                if (response?.isSuccessful) {
                                    Log.d("FILE : ", imageFile.toString())
                                    Toast.makeText(this@PostBoardActivity, "File Uploaded Successfully", Toast.LENGTH_LONG).show()
                                    startActivity(intent)
                                } else {
                                    Log.d("result", "fail");
                                    Toast.makeText(this@PostBoardActivity, "Fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Log.d("FILE : ", imageFile.toString())
                                Log.d("FAIL", t.message);
                                Toast.makeText(this@PostBoardActivity, "완전 Fail", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                }
                override fun onFailure(call : Call<Board>, t:Throwable) {
                    Log.d("absolute fail", t.message)
                }
            })
        }

        /*image.setOnClickListener() {
            showAlert()
        }*/
    }

    // 스피너 설정
    inner class SpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            categoryData[position]
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    // 앨범접근
    private fun openGalley() {
        val intent : Intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, REQUEST_ALBUM)
    }

    // 카메라 촬영
    private fun takeCapture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile : File? = try {
                    createImageFile()
                } catch (e : IOException){
                    null
                }
                photoFile?.also {
                    val photoURI : Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.carrotmarket.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    // 이미지 파일 생성
    private fun createImageFile(): File? {
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        filename = MyData.getMember().nickname + "_"+ timestamp;
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
            .apply { curPhotoPath = absolutePath }
    }

    // 권한 설정
    private fun setPermission() {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(this@PostBoardActivity, "권한 허용", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@PostBoardActivity, "권한 거부", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 사용 권한 허용")
            .setDeniedMessage("권한을 거부하셨습니다.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    // 절대경로
    private fun getRealPathFromURI(uri: Uri): String {
        var columnIndex = 0
        var proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    // 이미지 파일 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 카메라에서 사진 가져온 후 실행
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap : Bitmap
            val file = File(curPhotoPath)
            imageFile = file
            pictures += filename + " ";
            pictureList.add(filename)
            ++cntImage
            var requestBody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            images.add(MultipartBody.Part.createFormData("imageFile", filename, requestBody))

            if(Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                //image.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                //image.setImageBitmap(bitmap)
                bitmapImages.add(bitmap)
            }
            //savePhoto(bitmap)
        }

        // 앨범에서 사진 가져온 후 실행
        else if(requestCode == REQUEST_ALBUM && resultCode == Activity.RESULT_OK) {

            var selectImage : Uri? = data?.data
            val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            filename = MyData.getMember().nickname + "_"+ timestamp;
            val cur = getRealPathFromURI(selectImage!!)

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectImage)
                //image.setImageBitmap(bitmap)
                bitmapImages.add(bitmap)
                imageFile = File(cur)
                pictures += filename + " ";
                pictureList.add(filename)
                var requestBody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(cur))
                images.add(MultipartBody.Part.createFormData("imageFile", filename, requestBody))
            } catch (e:Exception) {
                Toast.makeText(this@PostBoardActivity, "이미지 불러오기 실패", Toast.LENGTH_LONG).show()
            }
        }
    }

    // 앨범에 저장
    private fun savePhoto(bitmap: Bitmap) {
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Android/data/com.example.carrotmarket/files/Pictures/"
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory) {
            folder.mkdir()
        }

        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(this@PostBoardActivity, "앨범 저장", Toast.LENGTH_SHORT).show()
    }

    private fun calculateDeadLine(regDate : String, date : Int) : String {
        var year = regDate.slice(IntRange(0,3)).toInt()
        var month = regDate.slice(IntRange(4,5)).toInt()
        var day = regDate.slice(IntRange(6,7)).toInt()

        Log.d("deadline", year.toString() + month.toString() + day.toString())

        day += date
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if(day > 31) {
                day -= 31
                month += 1
            }
        } else if(month == 2) {
            if(day > 28) {
                day -= 28
                month += 1
            }
        } else {
            if(day > 30) {
                day -= 30
                month += 1
            }
        }

        if(month > 12) {
            month = 1
            year += 1
        }

        var m = month.toString()
        if(month < 10)
            m = "0" + m
        var d = day.toString()
        if(day < 10)
            d = "0" + d


        Log.d("deadline", year.toString() + m + d)
        return year.toString() + m + d
    }

    private fun showAlert() {
        val myAlert = AlertDialog.Builder(this)
        myAlert.setTitle("사진 선택")
        myAlert.setMessage("사진을 삭제하시겠습니까?")
        myAlert.setPositiveButton("예") {
            dialogInterface : DialogInterface, i : Int ->
            images.removeAt(images.lastIndex)
        }
        myAlert.setNegativeButton("취소") {
            dialogInterface : DialogInterface, i : Int ->
            Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show()
        }
        myAlert.show()
    }
}