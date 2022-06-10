package com.example.mybox.data.remote.responses

data class JsonMessage(
    val content: String,
    val creation_time: String,
    val id_chat: Int,
    val id_message: Int,
    val id_user: Int
)