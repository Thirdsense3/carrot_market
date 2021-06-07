package com.example.carrotmarket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


private const val NUM_PAGES = 5
private const val TAG = "BoardActivity"

class BoardActivity: AppCompatActivity() {
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)
    lateinit var board: Board

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        viewPager = findViewById(R.id.boardPager)

        val toolbar:Toolbar = findViewById(R.id.toolbar)
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

                        board = Board(it.id, it.price, it.title, it.text, it.categoryId, it.locationX, it.locationY, it.nickname, it.registerDate, it.deadlineDate, it.dibsCnt, it.viewCnt, it.chatCnt, it.picture)

                    }

                    boardNickName.text = board.nickname
                    boardAddress.text = board.locationX.toString()
                    boardTitle.text = board.title
                    boardContents.text = board.text
                    boardPrice.text = board.price.toString() + "원"

                    var cnt:String = ""

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

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater:MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        return when(item.itemId){
            R.id.boardDelete -> {
                myAPI.deleteBoard(board.id).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response?.isSuccessful) {
                            Log.d("BoardActivity", response.toString())
                            Log.d("BoardActivity", call.toString())
    
                            Toast.makeText(this@BoardActivity,"삭제되었습니다.",Toast.LENGTH_SHORT).show()

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
                Toast.makeText(this,"수정하기 클릭",Toast.LENGTH_SHORT).show()
                true
            }
            else ->{
                Toast.makeText(this,"나머지 버튼 클릭",Toast.LENGTH_SHORT).show()
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
                    board = Board(it.id, it.price, it.title, it.text, it.categoryId, it.locationX, it.locationY, it.nickname, it.registerDate, it.deadlineDate, it.dibsCnt, it.viewCnt, it.chatCnt, it.picture)
                }
            }

        })

        return board
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {

//            when(position) {
//                0 -> ScreenSlidePageFragment(R.drawable.hashtag_image)
//                1 -> ScreenSlidePageFragment(R.drawable.nomad_image)
//                else -> ScreenSlidePageFragment(R.drawable.record_image)
            }
        }
    }

}