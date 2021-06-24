package com.example.carrotmarket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.dto.Chat
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class ChatAdapter(private var chatList: MutableList<Chat>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var view: View
        val context = parent.context

        Log.d("ViewType:", viewType.toString())

        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.room_center_item_list, parent,false)
            return CenterViewHolder(view)
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.room_left_item_list, parent,false)
            return LeftViewHolder(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.room_right_item_list, parent,false)
            return RightViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CenterViewHolder) {
            holder.textv.text = chatList[position].message
        } else if (holder is LeftViewHolder) {
            holder.textv_nickname.text = chatList[position].nickname
            holder.textv_msg.text = chatList[position].message
            holder.textv_time.text = Date(chatList[position].unix * 1000).toString()
        } else if (holder is RightViewHolder) {
            holder.textv_msg2.text = chatList[position].message
            holder.textv_time2.text = Date(chatList[position].unix * 1000).toString()
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (chatList[position].id == -1) {
            return 0
        } else if (chatList[position].id == 1) {
            // 현재 로그인한 계정과 같을 때로 변경 필요.
            return 2
        } else {
            return 1
        }
    }

    class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textv = itemView.findViewById<TextView>(R.id.textv)
    }

    class LeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgv = itemView.findViewById<CircleImageView>(R.id.imgv)
        var textv_nickname = itemView.findViewById<TextView>(R.id.textv_nickname)
        var textv_msg = itemView.findViewById<TextView>(R.id.textv_msg)
        var textv_time = itemView.findViewById<TextView>(R.id.textv_time)
    }

    class RightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textv_msg2 = itemView.findViewById<TextView>(R.id.textv_msg2)
        var textv_time2 = itemView.findViewById<TextView>(R.id.textv_time2)
    }
}