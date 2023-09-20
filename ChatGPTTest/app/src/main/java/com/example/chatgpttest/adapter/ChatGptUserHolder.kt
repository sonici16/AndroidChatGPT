package com.example.chatgpttest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.chatgpttest.databinding.HolderUserChatgptBinding
import com.example.chatgpttest.message.UserMessage

class ChatGptUserHolder (private val binding: HolderUserChatgptBinding, var mContext: Context) : RecyclerView.ViewHolder(binding.root){


    fun setData(pData: UserMessage) {
        pData?.let { data ->
            binding.userMessageText.text = pData.content
        }
    }

}