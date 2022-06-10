package com.example.mybox.allscreens.chat_screen

import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.JsonChat
import com.example.mybox.data.remote.responses.JsonId
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {


    suspend fun getMessages(
        accessToken: String,
        chatId: Int,
    ):Resource<JsonChat>{
        return repository.getChat(chatId = chatId, accessToken =  "Bearer $accessToken")
    }

    suspend fun postSendMessage(
        accessToken: String,
        chatId: Int,
        content: String,
    ):Resource<JsonId>{
        val jsonObject = JSONObject()
        jsonObject.put("content", content)
        val jsonObjectString = jsonObject.toString()
        val buuBodyRequest = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return repository.postSendMessage(chatID = chatId, requestBody = buuBodyRequest, accessToken = "Bearer $accessToken")
    }
}