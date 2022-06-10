package com.example.mybox.allscreens.catalog_screen

import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.JsonCheck
import com.example.mybox.data.remote.responses.JsonItem
import com.example.mybox.data.remote.responses.JsonOrderId
import com.example.mybox.data.remote.responses.JsonStatus
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {



    suspend fun getItem(
        userId: Int,
        itemId: Int,
        accessToken: String

    ):Resource<JsonItem>{
        return repository.getItem(userId = userId, id_item = itemId,accessToken="Bearer $accessToken")
    }

    suspend fun getCheckFavorite(
        userId: Int,
        itemId: Int,
        accessToken: String

    ):Resource<JsonCheck>{
        return repository.getCheckFavorite(userId = userId, id_item = itemId, accessToken="Bearer $accessToken")
    }

    suspend fun postAddFavorite(
        userId: Int,
        itemId: Int,
        accessToken: String
    ):Resource<JsonStatus> {
        return repository.postAddFavorite(userId = userId, id_item = itemId, accessToken="Bearer $accessToken")
    }

    suspend fun postDeleteFavorite(
        userId: Int,
        itemId: Int,
        accessToken: String
    ):Resource<JsonStatus> {
        return repository.postDeleteFavorite(userId = userId, id_item = itemId, accessToken="Bearer $accessToken")
    }

    suspend fun postCreateOrder(
        ownerId: Int,
        itemID: Int,
        description: String,
        accessToken: String
    ):Resource<JsonOrderId>{
        val jsonObject = JSONObject()
        jsonObject.put("id_user_owner", ownerId)
        jsonObject.put("id_item", itemID)
        jsonObject.put("description", description)
        val jsonObjectString = jsonObject.toString()
        val buuBodyRequest = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return repository.postCreateOrder(requestBody = buuBodyRequest,accessToken="Bearer $accessToken" )
    }
}