package com.sendiko.ternaqu.repository.chat

data class Chat(
    val id: Int,
    val name: String,
    val message: String,
    val date: String,
    val profileUrl: String
)
