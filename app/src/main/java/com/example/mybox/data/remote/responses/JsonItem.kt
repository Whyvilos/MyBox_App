package com.example.mybox.data.remote.responses

data class JsonItem(
    val price: Int?,
    val status: String,
    val description: String,
    val title: String,
    val url_media: String?,
    val id_item: Int,
    val id_user: Int?,
)