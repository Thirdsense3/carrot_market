package com.example.carrotmarket.chat

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.RecyclerAdapter
import com.example.carrotmarket.dto.chat.BaseMessage

abstract class MessageListAdapter(
    private val mContext: Context,
    private val mMessageList: List<BaseMessage>
) : RecyclerView.Adapter<RecyclerAdapter.Holder>()