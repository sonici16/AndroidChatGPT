package com.example.chatgpttest.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgpttest.message.AssistantMessage
import com.example.chatgpttest.message.ChatMessage
import com.example.chatgpttest.message.ErrorMessage
import com.example.chatgpttest.message.LoadingMessage
import com.example.chatgpttest.message.UserMessage
import com.example.chatgpttest.network.ChatGptService
import com.example.chatgpttest.room.ChatRequest
import com.example.chatgpttest.room.ChatResponse
import com.example.chatgpttest.room.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _chatMessages = MutableLiveData<List<ChatMessage>>()
    val chatMessages: LiveData<List<ChatMessage>> = _chatMessages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendMessage(message: UserMessage) {

        viewModelScope.launch(Dispatchers.IO) {
            val currentMessages = _chatMessages.value.orEmpty().toMutableList()
            val userMessage = UserMessage(message.content)
            currentMessages.add(userMessage)
            _chatMessages.postValue(currentMessages)

            currentMessages.add(LoadingMessage(message.content))
            _chatMessages.postValue(currentMessages) // 메시지 전송 중 로딩 시작

            val request = ChatRequest("gpt-3.5-turbo", currentMessages as ArrayList<ChatMessage>)
            ChatGptService.api.getChatCompletion(request).enqueue(object : Callback<ChatResponse> {
                override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                    if (response.isSuccessful) {
                        val chatResponse = response.body()
                        val assistantMessage = chatResponse?.choices?.firstOrNull()?.message
                        if (assistantMessage != null) {

                            currentMessages.removeAt(currentMessages.size-1)

                            currentMessages.add(AssistantMessage(assistantMessage.content))
                            _chatMessages.postValue(currentMessages)
                        }
                    } else {
                        // 요청이 실패한 경우 처리
                        val body  = response.errorBody()
                        val gson = Gson()
                        val errorObject = gson.fromJson(body!!.string(), ErrorResponse::class.java)

                        val errorMessage = ErrorMessage(errorObject.error.message)
                        currentMessages.removeAt(currentMessages.size-1)

                        currentMessages.add(errorMessage)
                        _chatMessages.postValue(currentMessages)
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    // 통신 오류 처리
                    val errorMessage = ErrorMessage(t.stackTraceToString())
                    currentMessages.add(errorMessage)
                    _chatMessages.postValue(currentMessages)
                }
            })
        }
    }
}