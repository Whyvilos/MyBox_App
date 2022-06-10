package com.example.mybox.allscreens.sign_in_screen

import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.JsonToken
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignInModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {
    suspend fun postSignIn(
        login: String,
        password: String
    ): Resource<JsonToken> {
        val jsonObject = JSONObject()
        jsonObject.put("username", login)
        jsonObject.put("password", password)
        val jsonObjectString = jsonObject.toString()
        val buuBodyRequest = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return repository.postSignIn(buuBodyRequest)
    }
}
