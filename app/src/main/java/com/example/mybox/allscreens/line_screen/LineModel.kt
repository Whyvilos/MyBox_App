package com.example.mybox.allscreens.line_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.Post
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LineModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {

    var lineList = mutableStateOf<MutableList<Post>>(mutableListOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    var isloaded = mutableStateOf(true)
    fun changeFlag() {
        isloaded.value = !isloaded.value
    }

    suspend fun getLine(
        accessToken: String
    ){

        var result = repository.getLine("Bearer $accessToken")
        when (result) {
            is Resource.Success -> {
                if(result.data!!.data != null) {
                    val postEntries = result.data!!.data.mapIndexed { index, entry ->
                        val creation_time: String = entry.creation_time
                        val description: String = entry.description
                        val id_item = entry.id_item
                        val url_media = entry.url_media
                        val price = entry.price
                        val id_user = entry.id_user
                        val username = entry.username
                        Post(
                            creation_time = creation_time,
                            description = description,
                            id_item = id_item,
                            price = price,
                            id_user = id_user,
                            url_media = url_media,
                            username = username
                        )
                    }
                    loadError.value = ""
                    isLoading.value = false
                    lineList.value += postEntries
                }
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                Log.e("Error: ", "Error ${loadError.value}")
                isLoading.value = false
            }
        }
    }
}