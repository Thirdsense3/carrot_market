package com.example.carrotmarket

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "RegisterActivity"
const val REQUEST_IMAGE_CAPTURE = 1;

class RegisterActivity : AppCompatActivity() {

    lateinit var currentPhotoPath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 회원등록 레이아웃으로 교체

//        button.setOnClickListener{
//            if(checkPermission()){
//                dispatchTakePictureIntent()
//            }
//            else{
//                requestPermission()
//            }
//        }

    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE,CAMERA), REQUEST_IMAGE_CAPTURE);
    }

    private fun checkPermission():Boolean{
        return(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d("TAG","permission : "+permissions[0] + " is" + grantResults[0] + "카메라 허용")
        }
        else{
            Log.d("TAG","카메라 거부")
        }
    }

    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if(takePictureIntent.resolveActivity(this.packageManager) != null){
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex:IOException){
                        Log.d("TAG", "이미지 파일 생성중 오류")
                        null
                    }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this, "com.example.carrotmarket", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir:File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timestamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath=absolutePath
        }
    }
}

class Member(email: String, password: String, name: String, nickname: String, /*photo,*/ iscorretphonenumber: Boolean, town : String){
    var email : String ="";
    var password: String ="";
    var name : String="";
    var nickname : String="";
    var photo="";
    var iscorretphonenumber : Boolean =false;
    var town : String ="";

    init {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        /*this.photo = photo;*/
        this.iscorretphonenumber = iscorretphonenumber;
        this.town = town;

    }
}