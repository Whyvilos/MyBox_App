package com.example.mybox.data.remote.responses

data class Post(
    val creation_time: String,
    val description: String,
    val url_media: String?,
    val id_item: Int?,
    val price: Int?,
    val id_user: Int,
    val username: String?
)