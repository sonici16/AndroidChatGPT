package com.example.chatgpttest.message

sealed class ChatMessage {
     abstract var role: String
    abstract val content: String
}

data class UserMessage(override val content: String) : ChatMessage() {
    override var role: String = "user"
}

data class AssistantMessage(override val content: String) : ChatMessage() {
     override var role: String = "assistant"
}
data class ErrorMessage(override val content: String) : ChatMessage() {
     override var role: String = "Error"
}
data class LoadingMessage(override val content: String) : ChatMessage() {
    override var role: String = "user"
}