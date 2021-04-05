package com.example.carrotmarket
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrotmarket.dto.AccountSharedPreferences
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.android.synthetic.main.activity_boardlist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity: AppCompatActivity() {
    private val TAG = "BoardActivity"
    var boardlist = mutableListOf<Board>(
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,""),
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,""),
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,""),
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,""),
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,""),
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,""),
            Board(2,2222,"test2","test2",2,2.toFloat(),2.toFloat(),"test2","11111111","11111116",2,2,2,"")
    )
    val url = "http://10.0.2.2:8080/login"
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardlist)

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        getBoardList()

        CoroutineScope(IO).launch {

            withContext(Main){
                val boardAdapter = RecyclerAdapter(this@ListActivity,boardlist){
                    val intent = Intent(this@ListActivity,BoardActivity::class.java)
                    /**
                     * id를 string으로 변환후 넘김
                     * */
                    intent.putExtra(it.id.toString(),it.title)
                    startActivity(intent)
                }
                boardRecyclerView.adapter = boardAdapter

                val lm = LinearLayoutManager(this@ListActivity)
                boardRecyclerView.layoutManager = lm
                boardRecyclerView.setHasFixedSize(true)
            }
        }

        logoutButton.setOnClickListener {
            AccountSharedPreferences.deleteUserData(this)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getBoardList(){
        myAPI.boardList().enqueue(object : Callback<List<Board>>{
            override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Board>>, response: Response<List<Board>>) {
                response.body()?.let {
                    for (item in it){

                        val board = Board(item.id,item.price,item.title,item.text,item.categoryId,item.locationX,item.locationY,item.nickname,item.registerDate,item.deadLineDate,item.dibsCnt,item.viewCnt,item.chatCnt,item.picture)
                        item.picture = "carrot"

                        Log.d(TAG, "item : ${item.id}")
                        Log.d(TAG, "item : ${item.price}")
                        Log.d(TAG, "item : ${item.title}")
                        Log.d(TAG, "item : ${item.text}")
                        Log.d(TAG, "item : ${item.categoryId}")
                        Log.d(TAG, "item : ${item.locationX}")
                        Log.d(TAG, "item : ${item.locationY}")
                        Log.d(TAG, "item : ${item.nickname}")
                        Log.d(TAG, "item : ${item.registerDate}")
                        Log.d(TAG, "item : ${item.deadLineDate}")
                        Log.d(TAG, "item : ${item.dibsCnt}")
                        Log.d(TAG, "item : ${item.picture}")

                        boardlist.add(board)
                    }
                }
            }
        })
    }

}
