package com.example.chatgpttest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatGptService {
    private const val BASE_URL = "https://api.openai.com/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val api: ChatGptApi = retrofit.create(ChatGptApi::class.java)
}


