package com.example.carrotmarket.chat

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.RecyclerAdapter
import com.example.carrotmarket.dto.chat.ChatMessage

abstract class MessageListAdapter(
    private val mContext: Context,
    private val mMessageList: List<ChatMessage>
) : RecyclerView.Adapter<RecyclerAdapter.Holder>()