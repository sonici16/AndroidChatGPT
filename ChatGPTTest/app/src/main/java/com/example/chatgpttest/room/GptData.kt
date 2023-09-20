package com.example.chatgpttest.room

import android.os.Parcelable
import com.example.chatgpttest.message.ChatMessage
import com.example.chatgpttest.message.UserMessage
import kotlinx.parcelize.Parcelize

data class Message(
    val role: String,
    val content: String,
    val isError: Boolean = false
    )

data class ChatRequest(
    val model: String,
    val messages: ArrayList<ChatMessage>
    )

data class ChatResponse(
    val id: String,
    val choices: List<Choice>
    )

data class Choice(
    val message: Message
    )

@Parcelize
data class ErrorResponse(
    val error: ErrorDetails
): Parcelable

@Parcelize
data class ErrorDetails(
    val message: String
): Parcelable