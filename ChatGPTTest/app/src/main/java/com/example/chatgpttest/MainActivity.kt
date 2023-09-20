package com.example.chatgpttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgpttest.adapter.ChatGptAdapter
import com.example.chatgpttest.databinding.ActivityMainBinding
import com.example.chatgpttest.message.UserMessage
import com.example.chatgpttest.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var messageAdapter: ChatGptAdapter
    private lateinit var chatViewModel: MainViewModel

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // ChatViewModel 인스턴스 생성
        chatViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // RecyclerView 설정
        messageAdapter = ChatGptAdapter(this)
        binding.messagesRV.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        binding.messagesRV.adapter = messageAdapter


        // 메시지를 보내는 버튼 클릭 이벤트 처리
        binding.btnSend.setOnClickListener {

            if (binding.messageInput.text.isNotEmpty()) {
                val userMessage = UserMessage(binding.messageInput.text.toString())

                // ChatViewModel을 사용하여 메시지 전송
                chatViewModel.sendMessage(userMessage)

                // 사용자 메시지를 RecyclerView에 추가
                messageAdapter.addMessage(userMessage)
                binding.messageInput.text.clear() // 입력란 비우기
            }

        }

        // ChatViewModel에서 LiveData를 관찰하여 채팅 응답 처리
        chatViewModel.chatMessages.observe(this) { messageList ->
            messageList?.let {
                messageAdapter.submitList(it)
                binding.messagesRV.scrollToPosition(it.size - 1) // 새 메시지로 스크롤
            }
        }

    }
}