package com.example.carrotmarket

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class RecyclerAdapter(private val context: Context, private var boardList: MutableList<Board>, val itemClick: (Board) -> Unit):RecyclerView.Adapter<RecyclerAdapter.Holder>(){

    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)
    private val TAG = "RecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        /**화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성한다.**/
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return Holder(view,itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        /**위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.*/
        holder.bind(boardList[position],context)
    }

    override fun getItemCount(): Int {
        /**RecyclerView로 만들어지는 item의 총 개수를 반환한다.*/
        return boardList.size
    }

    fun update(updated : MutableList<Board>){
        CoroutineScope(Dispatchers.Main).launch {
            val diffResult = async(Dispatchers.IO){
                getDiffResult(updated)
            }
            boardList = updated
            diffResult.await().dispatchUpdatesTo(this@RecyclerAdapter)
        }
    }

    private fun getDiffResult(updated: MutableList<Board>): DiffUtil.DiffResult{
        val diffCallback =ListDiffCallback(boardList,updated)
        return DiffUtil.calculateDiff(diffCallback)
    }

    fun getItem(position: Int) = boardList[position]

    class ListDiffCallback(private val oldList: MutableList<Board>, private val newList: MutableList<Board>) : DiffUtil.Callback(){
        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
    }

    inner class Holder(itemView: View?, itemClick: (Board) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        private val boardPhoto = itemView?.findViewById<ImageView>(R.id.boardPhotoImg)
        private val boardTitle = itemView?.findViewById<TextView>(R.id.boardName)
        private val boardLocationX = itemView?.findViewById<TextView>(R.id.boardLocation)
        private val boardPrice = itemView?.findViewById<TextView>(R.id.boardPrice)

        @SuppressLint("SetTextI18n")
        fun bind(board: Board, context: Context) {
            /* Photo의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고, 이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/

            if (board.picture != "") {
                /**val resourceId = context.resources.getIdentifier(board.picture, "drawable", context.packageName)*/

                myAPI.getPreviewImage(board.id).enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d(TAG, t.message)
                        Log.d(TAG, "썸네일 연결 실패")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        response.body()?.let {
                            // TODO("spring 에서 파일 보낼때 확장자명 jpg 붙여서 보내기")
                            Log.d(TAG, "item : $it")
                            val inputStream = it.byteStream()
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            boardPhoto?.setImageBitmap(bitmap)
                        }
                    }
                } )

            } else{
                Log.d(TAG, "picture비었음")
                boardPhoto?.setImageResource(R.drawable.carrot)
            }

            boardTitle?.text = board.title
            boardLocationX?.text = board.locationX.toString()
            boardPrice?.text = board.price.toString() + "원"

            itemView.setOnClickListener{itemClick(board)}
        }
    }

}