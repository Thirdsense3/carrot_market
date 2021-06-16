package com.example.carrotmarket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val NUM_PAGES = 5
private const val TAG = "BoardActivity"

class BoardActivity: AppCompatActivity() {
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)
    lateinit var board: Board
    var imagesString =  mutableListOf<String>()

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var sliderViewPager: ViewPager2
    private lateinit var layoutIndicator: LinearLayout


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (intent.hasExtra("board")) {
            val boardId = intent.extras?.getLong("board")
            Log.d(TAG, "intent : $boardId")

            myAPI.getBoard(boardId).enqueue(object : Callback<Board> {
                override fun onFailure(call: Call<Board>, t: Throwable) {
                    Log.d(TAG, t.message)
                    Log.d(TAG, "getBoard 실패")
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Board>, response: Response<Board>) {
                    response.body()?.let {
                        Log.d(TAG, "item : ${it.id}")
                        Log.d(TAG, "item : ${it.price}")
                        Log.d(TAG, "item : ${it.title}")
                        Log.d(TAG, "item : ${it.text}")
                        Log.d(TAG, "item : ${it.categoryId}")
                        Log.d(TAG, "item : ${it.locationX}")
                        Log.d(TAG, "item : ${it.locationY}")
                        Log.d(TAG, "item : ${it.nickname}")
                        Log.d(TAG, "item : ${it.registerDate}")
                        Log.d(TAG, "item : ${it.deadlineDate}")
                        Log.d(TAG, "item : ${it.dibsCnt}")
                        Log.d(TAG, "item : ${it.picture}")

                        board = Board(
                            it.id,
                            it.price,
                            it.title,
                            it.text,
                            it.categoryId,
                            it.locationX,
                            it.locationY,
                            it.nickname,
                            it.registerDate,
                            it.deadlineDate,
                            it.dibsCnt,
                            it.viewCnt,
                            it.chatCnt,
                            it.picture
                        )

                    }

                    boardNickName.text = board.nickname
                    boardAddress.text = board.locationX.toString()
                    boardTitle.text = board.title
                    boardContents.text = board.text
                    boardPrice.text = board.price.toString() + "원"

                    Log.d(TAG,"board picture 확인 : ${board.picture}")

                    val stringTmp = board.picture.split(" ")

                    Log.d(TAG,"board Split 확인 : $stringTmp")

                    val baseURL = "http://10.0.2.2:8080/"

                    for (index in stringTmp) {
                        if(index==""){
                            continue;
                        }
                        val str: String = "${baseURL}/download/${board.id}/${index}"
                        Log.d(TAG, str)
                        imagesString.add(str)
                    }

                    sliderViewPager = findViewById(R.id.sliderViewPager)
                    layoutIndicator = findViewById(R.id.layoutIndicators);

                    sliderViewPager.offscreenPageLimit = 1
                    sliderViewPager.adapter = ImageSliderAdapter(this@BoardActivity, imagesString)

                    sliderViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            setCurrentIndicator(position)
                        }
                    })

                    setupIndicators(imagesString.size-1)

                    var cnt: String = ""

                    if (board.chatCnt >= 0) {
                        cnt += "채팅 " + board.chatCnt + " "

                        if (board.dibsCnt >= 0) {
                            cnt += "관심 " + board.dibsCnt + " "

                            if (board.viewCnt >= 0) {
                                cnt += "조회 " + board.viewCnt
                            }
                        }
                    }

                    boardOtherCnt.text = cnt

                }

            })

        } else {
            Toast.makeText(this@BoardActivity, "게시물 id 오류", Toast.LENGTH_SHORT).show()
        }

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.boardDelete -> {
                myAPI.deleteBoard(board.id).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response?.isSuccessful) {
                            Log.d("BoardActivity", response.toString())
                            Log.d("BoardActivity", call.toString())

                            Toast.makeText(this@BoardActivity, "삭제되었습니다.", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(this@BoardActivity, ListActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            Log.d("BoardActivity", "fail")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("FILE : ", call.toString())
                        Log.d("FAIL", t.message)
                    }
                })
                true
            }
            R.id.boardEdit -> {
                Toast.makeText(this, "수정하기 클릭", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                Toast.makeText(this, "나머지 버튼 클릭", Toast.LENGTH_SHORT).show()
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun getBoard(id: Long?): Board {
        lateinit var board: Board

        myAPI.getBoard(id).enqueue(object : Callback<Board> {
            override fun onFailure(call: Call<Board>, t: Throwable) {
                Log.d(TAG, t.message)
                Log.d(TAG, "getBoard 실패")
            }

            override fun onResponse(call: Call<Board>, response: Response<Board>) {
                response.body()?.let {
                    board = Board(
                        it.id,
                        it.price,
                        it.title,
                        it.text,
                        it.categoryId,
                        it.locationX,
                        it.locationY,
                        it.nickname,
                        it.registerDate,
                        it.deadlineDate,
                        it.dibsCnt,
                        it.viewCnt,
                        it.chatCnt,
                        it.picture
                    )
                }
            }

        })

        return board
    }
    private fun setupIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(16, 8, 16, 8)
        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = params
            layoutIndicator.addView(indicators[i])
        }
        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount: Int = layoutIndicator.childCount
        for (i in 0 until childCount) {
            val imageView: ImageView = layoutIndicator.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                    )
                )
            }
        }
    }

}