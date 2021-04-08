package com.example.carrotmarket

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.dto.Board

class RecyclerAdapter(private val context: Context, private val boardList: MutableList<Board>, val itemClick: (Board) -> Unit):RecyclerView.Adapter<RecyclerAdapter.Holder>(){

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

    inner class Holder(itemView: View?, itemClick: (Board) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        private val boardPhoto = itemView?.findViewById<ImageView>(R.id.boardPhotoImg)
        private val boardTitle = itemView?.findViewById<TextView>(R.id.boardName)
        private val boardLocationX = itemView?.findViewById<TextView>(R.id.boardLocation)
        private val boardPrice = itemView?.findViewById<TextView>(R.id.boardPrice)

        @SuppressLint("SetTextI18n")
        fun bind(board: Board, context: Context) {
            /* Photo의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
          이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
            if (board.picture != "") {
                val resourceId = context.resources.getIdentifier(board.picture, "drawable", context.packageName)
                boardPhoto?.setImageResource(resourceId)
            } else{
                boardPhoto?.setImageResource(R.drawable.carrot)
            }

            boardTitle?.text = board.title
            // TODO (locationX, locationY 변환 필요)
            boardLocationX?.text = board.locationX.toString()
            boardPrice?.text = board.price.toString() + "원"

            itemView.setOnClickListener{itemClick(board)}
        }
    }


}