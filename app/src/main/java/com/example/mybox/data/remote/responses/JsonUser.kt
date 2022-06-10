package com.example.mybox.data.remote.responses

data class JsonUser(
    val id_user: Int?,
    val isyou: Boolean,
    val mail: String,
    val name: String,
    val password: String,
    val url_avatar: String,
    val phone: Any,
    val rank: Boolean,
    val username: String
)