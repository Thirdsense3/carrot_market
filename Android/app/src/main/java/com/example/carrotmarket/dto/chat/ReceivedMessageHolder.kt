package com.example.carrotmarket.dto.chat

import android.view.View
import android.widget.ImageView

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

//https://sendbird.com/developer/tutorials/android-chat-tutorial-building-a-messaging-ui 참고

private class ReceivedMessageHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var messageText: TextView
    var timeText: TextView
    var nameText: TextView
    var profileImage: ImageView
    fun bind(message: UserMessage) {
        messageText.setText(message.getMessage())

        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
        nameText.setText(message.getSender().getNickname())

        // Insert the profile image from the URL into the ImageView.
        Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage)
    }

    init {
        messageText = itemView.findViewById(R.id.text_gchat_user_other)
        timeText = itemView.findViewById(R.id.text_gchat_timestamp_other)
        nameText = itemView.findViewById(R.id.text_gchat_message_other)
        profileImage = itemView.findViewById(R.id.image_gchat_profile_other) as ImageView
    }
}