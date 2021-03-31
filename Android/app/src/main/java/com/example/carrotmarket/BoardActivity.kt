package com.example.carrotmarket
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.android.synthetic.main.activity_boardlist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardActivity: AppCompatActivity() {
    private val TAG = "BoardActivity"
    var boardlist = mutableListOf<Board>()
    val url = "http://10.0.2.2:8080/login"
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardlist)

        CoroutineScope(IO).launch {
            val resultBoard = getBoardList()

            withContext(Main){
                val boardAdapter = RecyclerAdapter(this@BoardActivity,boardlist){
                    TODO("게시물 클릭시 처리")
                }
                boardRecyclerView.adapter = boardAdapter

                val lm = LinearLayoutManager(this@BoardActivity)
                boardRecyclerView.layoutManager = lm
                boardRecyclerView.setHasFixedSize(true)
            }
        }
    }

    private suspend fun getBoardList(){
        myAPI.boardList().enqueue(object : Callback<List<Board>>{
            override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Board>>, response: Response<List<Board>>) {
                response.body()?.let {
                    for (item in it){
                        TODO("사진 경로 추가시 board 수정")
                        //val board = Board(item.id,item.price,item.title,item.text,item.categoryId,item.location,item.nickname,item.registerDate,item.deadlineDate,item.dibsCnt,item.viewCnt,item.chatCnt,item.photo)
                        //boardlist.add(board)
                    }
                }
            }
        })
    }

}
