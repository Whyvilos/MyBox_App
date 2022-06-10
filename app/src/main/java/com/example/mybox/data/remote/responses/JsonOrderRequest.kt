package com.example.mybox.data.remote.responses

data class JsonOrderRequest(
    val description: String,
    val id_item: Int,
    val id_user_owner: Int
)