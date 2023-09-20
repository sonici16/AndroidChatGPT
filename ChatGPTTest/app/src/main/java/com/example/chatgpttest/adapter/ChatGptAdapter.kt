package com.example.chatgpttest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatgpttest.databinding.HolderBotChatgptBinding
import com.example.chatgpttest.databinding.HolderUserChatgptBinding
import com.example.chatgpttest.message.AssistantMessage
import com.example.chatgpttest.message.ErrorMessage
import com.example.chatgpttest.message.LoadingMessage
import com.example.chatgpttest.message.UserMessage

class ChatGptAdapter(val mContext: Context) : ListAdapter<com.example.chatgpttest.message.ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        const val USER_MESSAGE = 1
        const val ASSISTANT_MESSAGE = 2
        const val ERROR_MESSAGE = 3
        const val LOADING_MESSAGE = 4
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            USER_MESSAGE -> ChatGptUserHolder(
                HolderUserChatgptBinding.inflate(
                    LayoutInflater.from(
                        mContext
                    ), parent, false
                ), mContext
            )

            ASSISTANT_MESSAGE -> ChatGptBotHolder(
                HolderBotChatgptBinding.inflate(
                    LayoutInflater.from(
                        mContext
                    ), parent, false
                ), mContext
            )

            ERROR_MESSAGE -> ChatGptBotHolder(
                HolderBotChatgptBinding.inflate(
                    LayoutInflater.from(
                        mContext
                    ), parent, false
                ), mContext
            )

            LOADING_MESSAGE -> ChatGptBotHolder(
                HolderBotChatgptBinding.inflate(
                    LayoutInflater.from(
                        mContext
                    ), parent, false
                ), mContext
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            USER_MESSAGE -> (holder as ChatGptUserHolder).setData(getItem(position) as UserMessage)
            ASSISTANT_MESSAGE -> (holder as ChatGptBotHolder).setData(getItem(position) as AssistantMessage)
            ERROR_MESSAGE -> (holder as ChatGptBotHolder).setData(getItem(position) as ErrorMessage)
            LOADING_MESSAGE -> (holder as ChatGptBotHolder).setData(getItem(position) as LoadingMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserMessage -> USER_MESSAGE
            is AssistantMessage -> ASSISTANT_MESSAGE
            is ErrorMessage -> ERROR_MESSAGE
            is LoadingMessage -> LOADING_MESSAGE

            else -> ASSISTANT_MESSAGE
        }
    }

    fun addMessage(chatMessage: com.example.chatgpttest.message.ChatMessage) {
        val currentList = currentList.toMutableList()
        currentList.add(chatMessage)
        submitList(currentList)
    }

    class MessageDiffCallback :
        DiffUtil.ItemCallback<com.example.chatgpttest.message.ChatMessage>() {
        override fun areItemsTheSame(
            oldItem: com.example.chatgpttest.message.ChatMessage,
            newItem: com.example.chatgpttest.message.ChatMessage
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: com.example.chatgpttest.message.ChatMessage,
            newItem: com.example.chatgpttest.message.ChatMessage
        ): Boolean {
            return oldItem.content == newItem.content
        }
    }

}








