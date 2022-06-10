package com.example.mybox.allscreens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.*
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {

    var listNotice = mutableStateOf<MutableList<JsonNotice>>(mutableListOf())
    var newNotice = mutableStateOf(0)
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    var isloaded = mutableStateOf(true)
    fun changeFlag() {
        isloaded.value = !isloaded.value
    }


    suspend fun postPing(
        accessToken: String,
    ): Resource<JsonPing> {
        var responce = repository.postPing("Bearer $accessToken")

        Log.e("PING: ", "${if(responce.data?.ping == true) "True" else responce.message}")
        return responce
    }

    suspend fun postUserId(
        accessToken: String,
    ): Resource<JsonUserId> {
        return repository.postUserId("Bearer $accessToken")
    }


    suspend fun getNotices(
        accessToken: String
    ) {
        when (val result = repository.getNotices("Bearer $accessToken")) {
            is Resource.Success -> {
                if (result.data!! != null) {
                    result.data.data?.let {
                        val noticeEntries = result.data.data.mapIndexed { index, entry ->
                            val id_notice = entry.id_notice
                            val content = entry.content
                            val status = entry.status
                            JsonNotice(
                                id_notice = id_notice,
                                status = status,
                                content = content
                            )
                        }
                        listNotice.value += noticeEntries
                        newNotice.value = result.data.count_new

                    }

                }
                loadError.value = ""
                isLoading.value = false
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                Log.e("Error: ", "get notice - ${loadError.value}")
                isLoading.value = false
            }
        }
    }
}