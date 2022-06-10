package com.example.mybox.allscreens.sign_up_screen

import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.JsonUserId
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignUpModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {

    suspend fun postSigUp(
        mail: String,
        name: String,
        username: String,
        password: String
    ): Resource<JsonUserId> {
        val jsonObject = JSONObject()
        jsonObject.put("mail", mail)
        jsonObject.put("name", name)
        jsonObject.put("username", username)
        jsonObject.put("password", password)
        val jsonObjectString = jsonObject.toString()
        val buuBodyRequest = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return repository.postSignUp(buuBodyRequest)
    }
}