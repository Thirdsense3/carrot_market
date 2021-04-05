package com.example.carrotmarket

import android.os.Bundle
import android.util.Log
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

class BoardActivity: AppCompatActivity() {
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)
    private val TAG ="BoardActivity"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        lateinit var board: Board

        GlobalScope.launch {
            if(intent.hasExtra("board")){
                val boardId = intent.extras?.getLong("board")
                board = getBoard(boardId)

            } else{
                Toast.makeText(this@BoardActivity,"게시물 id 오류",Toast.LENGTH_SHORT).show()
            }

            boardNickName.text = board.nickname
            boardAddress.text = board.locationX.toString()
            boardTitle.text = board.title
            boardContents.text = board.text
            lateinit var cnt:String

            if(board.chatCnt>=0){
                cnt+="채팅 " + board.chatCnt +" "
            }

            if(board.dibsCnt>=0){
                cnt+="관심 " + board.dibsCnt +" "
            }

            if(board.viewCnt>=0){
                cnt+="조회 " + board.viewCnt
            }

            boardOtherCnt.text = cnt
        }



    }

    private suspend fun getBoard(id:Long?):Board{
        var board: Board = async(Dispatchers.Main) {
            lateinit var boardTmp: Board

            myAPI.getBoard(id).enqueue(object : Callback<Board> {
                override fun onFailure(call: Call<Board>, t: Throwable) {
                    Log.d(TAG, t.message)
                    Log.d(TAG, "getBoard 실패")
                }

                override fun onResponse(call: Call<Board>, response: Response<Board>) {
                    response.body()?.let {
                        boardTmp.id = it.id
                        boardTmp.price = it.price
                        boardTmp.title = it.title
                        boardTmp.text = it.text
                        boardTmp.categoryId = it.categoryId
                        boardTmp.locationX = it.locationX
                        boardTmp.locationY = it.locationY
                        boardTmp.nickname = it.nickname
                        boardTmp.registerDate = it.registerDate
                        boardTmp.deadLineDate = it.deadLineDate
                        boardTmp.dibsCnt = it.dibsCnt
                        boardTmp.viewCnt = it.viewCnt
                        boardTmp.chatCnt = it.chatCnt
                        boardTmp.picture = it.picture
                    }

                }

            })
            boardTmp
        }

        return board
    }
}