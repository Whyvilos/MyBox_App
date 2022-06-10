package com.example.mybox.allscreens.menu_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.JsonId
import com.example.mybox.data.remote.responses.JsonItem
import com.example.mybox.data.remote.responses.JsonOrdersItem
import com.example.mybox.data.remote.responses.JsonStatus
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject




@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {


    var listItems = mutableStateOf<MutableList<JsonItem>>(mutableListOf())
    var listOrders = mutableStateOf<MutableList<JsonOrdersItem>>(mutableListOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var isloaded = mutableStateOf(true)
    fun changeFlag() {
        isloaded.value = !isloaded.value
    }

    suspend fun getFavorite(
        accessToken: String
    ) {
        when (val result = repository.getFavorite("Bearer $accessToken")) {
            is Resource.Success -> {
                if (result.data!!.data != null) {
                    val itemEntries = result.data.data.mapIndexed { index, entry ->
                        val title: String = entry.title
                        val description: String = entry.description
                        val status = entry.status
                        val url_media: String? = entry.url_media
                        val price = entry.price
                        val id_item = entry.id_item
                        val id_user = entry.id_user
                        JsonItem(
                            title = title,
                            description = description,
                            status = status,
                            price = price,
                            url_media = url_media,
                            id_item = id_item,
                            id_user = id_user

                        )
                    }
                    loadError.value = ""
                    isLoading.value = false
                    listItems.value += itemEntries
                }
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                Log.e("Error: ", "loadFavorite - ${loadError.value}")
                isLoading.value = false
            }
        }
    }

    suspend fun getOrders(
        accessToken: String
    ) {
        when (val result = repository.getOrders("Bearer $accessToken")) {
            is Resource.Success -> {
                if (result.data!! != null) {
                    val orderEntries = result.data.mapIndexed { index, entry ->
                        val title: String = entry.title
                        val description: String = entry.description
                        val status = entry.status
                        val url_media: String? = entry.url_media
                        val price = entry.price
                        val id_item = entry.id_item
                        val id_order = entry.id_order
                        val id_user_owner = entry.id_user_owner
                        JsonOrdersItem(
                            title = title,
                            description = description,
                            id_item = id_item,
                            status = status,
                            price = price,
                            url_media = url_media,
                            id_order = id_order,
                            id_user_owner = id_user_owner

                        )
                    }
                    loadError.value = ""
                    isLoading.value = false
                    listOrders.value += orderEntries
                }
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                Log.e("Error: ", "load orders - ${loadError.value}")
                isLoading.value = false
            }
        }
    }

    suspend fun getOrdersForYou(
        accessToken: String
    ) {
        when (val result = repository.getOrdersForYou("Bearer $accessToken")) {
            is Resource.Success -> {
                if (result.data!! != null) {
                    val orderEntries = result.data.mapIndexed { index, entry ->
                        val title: String = entry.title
                        val description: String = entry.description
                        val status = entry.status
                        val url_media: String? = entry.url_media
                        val price = entry.price
                        val id_item = entry.id_item
                        val id_order = entry.id_order
                        val id_user_owner = entry.id_user_owner
                        JsonOrdersItem(
                            title = title,
                            description = description,
                            id_item = id_item,
                            status = status,
                            price = price,
                            url_media = url_media,
                            id_order = id_order,
                            id_user_owner = id_user_owner

                        )
                    }
                    loadError.value = ""
                    isLoading.value = false
                    listOrders.value += orderEntries
                }
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                Log.e("Error: ", "load orders for you - ${loadError.value}")
                isLoading.value = false
            }
        }
    }

    suspend fun putOrderStatus(
        accessToken: String,
        orderId:Int,
        status: String
    ):Resource<JsonStatus>{
        return repository.putOrderStatus(orderId = orderId, status = status, accessToken = "Bearer $accessToken")
    }

    suspend fun getFindChat1(
        accessToken: String,
        orderId:Int
    ):Resource<JsonId>{
        return repository.getFindChatId1(orderId = orderId,  accessToken = "Bearer $accessToken")
    }

}