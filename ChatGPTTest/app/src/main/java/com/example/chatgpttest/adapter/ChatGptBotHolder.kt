package com.example.chatgpttest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.chatgpttest.databinding.HolderBotChatgptBinding
import com.example.chatgpttest.message.AssistantMessage
import com.example.chatgpttest.message.ErrorMessage
import com.example.chatgpttest.message.LoadingMessage

class ChatGptBotHolder (private val binding: HolderBotChatgptBinding, var mContext: Context) : RecyclerView.ViewHolder(binding.root){


    fun setData(pData: AssistantMessage) {
        pData?.let { data ->
            binding.assistantMessageText .text = pData.content
        }
    }
    fun setData(pData: ErrorMessage) {
        pData?.let { data ->
            binding.assistantMessageText.text = pData.content
        }
    }
    fun setData(pData: LoadingMessage) {
        pData?.let { data ->
            binding.assistantMessageText.text = "Loading..."
        }
    }

}