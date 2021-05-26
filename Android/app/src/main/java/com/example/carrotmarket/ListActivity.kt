package com.example.carrotmarket
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardlist)

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val postButton = findViewById<Button>(R.id.PostButton)
        val srcButton = findViewById<SearchView>(R.id.searchView)
        val spinner = findViewById<Spinner>(R.id.srcSpinner)
        var selectSrc = 0
        val listOfSpinner = arrayOf("제목+내용", "제목만")

        /**
         * https://doitddo.tistory.com/84
         * */

        getBoardList()

//        CoroutineScope(Dispatchers.Main).launch {
//            setAdapter(boardlist)
//        }

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

        spinner.adapter = ArrayAdapter(this, R.layout.spinner_item, listOfSpinner)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "onItemSelected: $position")
                selectSrc = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.e(TAG, "Nothing Selected")
            }
        }

        srcButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼 누를 때 호출
                isSearching = true
                val temp = query.toString().split(" ")
                var words = "%"
                for (tmp in temp) {
                    Log.d(TAG, tmp)
                    words += "$tmp%"
                }
                Log.d(TAG, "onQueryTextSubmit: $words")
                Log.d(TAG, "selectSrc: $selectSrc")

                if (selectSrc == 0) {
                    myAPI.searchBoard(words).enqueue(object : Callback<List<Board>> {
                        override fun onResponse(
                            call: Call<List<Board>>,
                            response: Response<List<Board>>
                        ) {
                            if (response?.isSuccessful) {
                                Log.d(TAG, response.toString())
                                Log.d(TAG, call.toString())
                                boardlist = mutableListOf<Board>()
                                response.body()?.let {
                                    for (item in it) {
                                        Log.d(TAG, item.title)
                                        Log.d(TAG, item.text)

                                        val board = Board(
                                            item.id,
                                            item.price,
                                            item.title,
                                            item.text,
                                            item.categoryId,
                                            item.locationX,
                                            item.locationY,
                                            item.nickname,
                                            item.registerDate,
                                            item.deadlineDate,
                                            item.dibsCnt,
                                            item.viewCnt,
                                            item.chatCnt,
                                            item.picture
                                        )
                                        boardlist.add(board)
                                    }
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    setAdapter(boardlist)
                                }
                            } else {
                                Log.d(TAG, "fail")
                            }
                        }

                        override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                            Log.d("FILE : ", call.toString())
                            Log.d("FAIL", t.message)
                        }
                    })
                }
                else {
                    myAPI.searchTitle(words).enqueue(object : Callback<List<Board>> {
                        override fun onResponse(
                            call: Call<List<Board>>,
                            response: Response<List<Board>>
                        ) {
                            if (response?.isSuccessful) {
                                Log.d(TAG, response.toString())
                                Log.d(TAG, call.toString())
                                boardlist = mutableListOf<Board>()
                                response.body()?.let {
                                    for (item in it) {
                                        Log.d(TAG, item.title)
                                        Log.d(TAG, item.text)

                                        val board = Board(
                                            item.id,
                                            item.price,
                                            item.title,
                                            item.text,
                                            item.categoryId,
                                            item.locationX,
                                            item.locationY,
                                            item.nickname,
                                            item.registerDate,
                                            item.deadlineDate,
                                            item.dibsCnt,
                                            item.viewCnt,
                                            item.chatCnt,
                                            item.picture
                                        )
                                        boardlist.add(board)
                                    }
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    setAdapter(boardlist)
                                }
                            } else {
                                Log.d(TAG, "fail")
                            }
                        }

                        override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                            Log.d("FILE : ", call.toString())
                            Log.d("FAIL", t.message)
                        }
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색창에서 글자가 변경이 일어날 때마다 호출
                // Log.d(TAG, "onQueryTextChange: $newText")
                return true
            }
        })

    }

    private fun setAdapter(boardList : List<Board>){
        val boardAdapter = RecyclerAdapter(this@ListActivity, boardlist) {
            val intent = Intent(this@ListActivity, BoardActivity::class.java)
            Log.d(TAG, "get Adapter -> ${it.id}")
            intent.putExtra("board", it.id)
            startActivity(intent)
        }

        boardRecyclerView.adapter = boardAdapter

        val lm = LinearLayoutManager(this@ListActivity)
        boardRecyclerView.layoutManager = lm
        boardRecyclerView.setHasFixedSize(true)
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        if (isSearching) {
            boardlist = mutableListOf<Board>()
            getBoardList()
//            CoroutineScope(Dispatchers.Main).launch {
//                setAdapter(boardlist)
//            }
            isSearching = false
        }
        else {
            var tempTime: Long = System.currentTimeMillis()
            var intervalTime: Long = tempTime - backPressedTime
            val toast = Toast.makeText(this@ListActivity, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)

            if (System.currentTimeMillis() > backPressedTime + 2000) {
                backPressedTime = System.currentTimeMillis()
                toast.show()
                return
            }

            if (System.currentTimeMillis() <= backPressedTime + 2000) {
                finishAffinity()
                toast.cancel()
                System.runFinalization()
                System.exit(0)
            }
        }
    }

    private fun getBoardList(){
        myAPI.boardList().enqueue(object : Callback<List<Board>>{
            override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                // TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Board>>, response: Response<List<Board>>) {
                if(response.isSuccessful){
                    val it = response.body()

                    if (it != null) {
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

                        CoroutineScope(Dispatchers.Main).launch {
                            setAdapter(boardlist)
                        }
                    }

                }


            }
        })
    }

}
