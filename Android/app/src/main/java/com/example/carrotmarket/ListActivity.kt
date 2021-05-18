package com.example.carrotmarket
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrotmarket.dto.AccountSharedPreferences
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.android.synthetic.main.activity_boardlist.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity: AppCompatActivity() {

    var backPressedTime : Long = 0;

    private val TAG = "ListActivity"
    var boardlist = mutableListOf<Board>()
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardlist)

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val postButton = findViewById<Button>(R.id.PostButton)

        /**
         * https://doitddo.tistory.com/84
         * */

        CoroutineScope(Dispatchers.Main).launch {
            val loadBoard = CoroutineScope(Dispatchers.Main).async{
                getAdapter()
            }.await()

            getBoardList()
        }

        logoutButton.setOnClickListener {
            AccountSharedPreferences.deleteUserData(this)
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        postButton.setOnClickListener() {
            val intent = Intent(this, PostBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        var tempTime : Long = System.currentTimeMillis()
        var intervalTime : Long = tempTime - backPressedTime
        val toast = Toast.makeText(this@ListActivity,"한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)

        if(System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            toast.show()
            return
        }

        if(System.currentTimeMillis() <= backPressedTime + 2000) {
            finishAffinity()
            toast.cancel()
            System.runFinalization()
            System.exit(0)
        }
    }

    private fun getBoardList(){
        myAPI.boardList().enqueue(object : Callback<List<Board>>{
            override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Board>>, response: Response<List<Board>>) {
                response.body()?.let {

                    for ((cnt, item) in it.withIndex()){
                        Log.d(TAG, "item $cnt")
                        Log.d(TAG, "item : ${item.id}")
                        Log.d(TAG, "item : ${item.price}")
                        Log.d(TAG, "item : ${item.title}")
                        Log.d(TAG, "item : ${item.text}")
                        Log.d(TAG, "item : ${item.categoryId}")
                        Log.d(TAG, "item : ${item.locationX}")
                        Log.d(TAG, "item : ${item.locationY}")
                        Log.d(TAG, "item : ${item.nickname}")
                        Log.d(TAG, "item : ${item.registerDate}")
                        Log.d(TAG, "item : ${item.deadlineDate}")
                        Log.d(TAG, "item : ${item.dibsCnt}")
                        Log.d(TAG, "item : ${item.picture}")

                        val board = Board(item.id,item.price,item.title,item.text,item.categoryId,item.locationX,item.locationY,item.nickname,item.registerDate,item.deadlineDate,item.dibsCnt,item.viewCnt,item.chatCnt,item.picture)

                        boardlist.add(board)
                    }
                }
            }
        })
    }

    private fun getAdapter(){
        val boardAdapter = RecyclerAdapter(this@ListActivity,boardlist){
            val intent = Intent(this@ListActivity,BoardActivity::class.java)
            Log.d(TAG,"get Adapter -> ${it.id}")
            intent.putExtra("board",it.id)
            startActivity(intent)
        }

        boardRecyclerView.adapter = boardAdapter

        val lm = LinearLayoutManager(this@ListActivity)
        boardRecyclerView.layoutManager = lm
        boardRecyclerView.setHasFixedSize(true)
    }

}
