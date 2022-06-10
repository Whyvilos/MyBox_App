package com.example.mybox.data.remote.responses

data class JsonOrdersItem(
    val description: String,
    val id_item: Int,
    val id_order: Int,
    val id_user_owner: Int,
    val price: Int?,
    val status: String,
    val title: String,
    val url_media: String?
)