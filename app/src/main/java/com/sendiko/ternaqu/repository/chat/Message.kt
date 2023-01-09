package com.sendiko.ternaqu.repository.chat

data class Message(
    val id: Int,
    val message: String,
    val time: String,
    val from: String,
    val isRead: Boolean,
)
