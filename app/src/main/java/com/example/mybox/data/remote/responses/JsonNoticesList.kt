package com.example.mybox.data.remote.responses

data class JsonNoticesList(
    val count_new: Int,
    val `data`: List<JsonNotice>
)