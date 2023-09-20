package com.example.chatgpttest.network

import com.example.chatgpttest.room.ChatRequest
import com.example.chatgpttest.room.ChatResponse
import retrofit2.Call
import retrofit2.http.*

interface ChatGptApi {
    @Headers(
        "Authorization: Bearer !!!!!!1", // 여기에 OpenAI API 키를 입력하세요
        "Content-Type: application/json"
    )
    @POST("chat/completions")
    fun getChatCompletion(@Body request: ChatRequest): Call<ChatResponse>
}