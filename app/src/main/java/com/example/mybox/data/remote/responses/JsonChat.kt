package com.example.mybox.data.remote.responses

data class JsonChat(
    val messages: List<JsonMessage>,
    val users: List<JsonUser>
)