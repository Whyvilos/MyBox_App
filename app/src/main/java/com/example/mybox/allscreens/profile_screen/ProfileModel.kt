package com.example.mybox.allscreens.profile_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mybox.data.remote.responses.*
import com.example.mybox.repository.MyBoxRepository
import com.example.mybox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject
import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Base64
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream


@HiltViewModel
class ProfileModel @Inject constructor(
    private val repository: MyBoxRepository
) : ViewModel() {

    var listPosts = mutableStateOf<MutableList<Post>>(mutableListOf())
    var listItems = mutableStateOf<MutableList<JsonItem>>(mutableListOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    var isloaded = mutableStateOf(true)
    fun changeFlag() {
        isloaded.value = !isloaded.value
    }

    suspend fun getUser(
        userId: Int,
        accessToken: String
    ): Resource<JsonUser> {
        return repository.getUser(userId, "Bearer $accessToken")
    }

    suspend fun postCreatePost(
        userId: Int,
        accessToken: String,
        imageBitmap: Bitmap?,
        description: String,
        time: String
    ): Resource<JsonPostId> {
        val jsonObject = JSONObject()
        jsonObject.put("description", description)
        jsonObject.put("creation_time", time)
        if(imageBitmap!=null){
            val bos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()
            Log.i(ContentValues.TAG, "bitmapToMultipart: ${Base64.encodeToString(bitmapdata, Base64.NO_WRAP)}")
            val name: RequestBody = bitmapdata.toRequestBody("image/*".toMediaTypeOrNull(), 0, bitmapdata.size)
            val image = MultipartBody.Part.createFormData("myFile", "avatar.jpg", name)
            val response = repository.uploadImagePost(image = image, accessToken = accessToken, userId = userId)
            when(response){
                is Resource.Success -> {
                    jsonObject.put("url_media", response.data!!.url_media)
                }
                is Resource.Error -> {
                    return Resource.Error(response.message!!)
                }
            }

        }
        val jsonObjectString = jsonObject.toString()
        val buuBodyRequest = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return repository.postCreatePost(userId, buuBodyRequest, "Bearer $accessToken")
    }

    suspend fun postCreateItem(
        userId: Int,
        accessToken: String,
        title: String,
        imageBitmap: Bitmap?,
        description: String,
        price: String,
    ): Resource<JsonItemId> {
        val jsonObject = JSONObject()
        jsonObject.put("title", title)
        if(imageBitmap!=null){
            val bos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()
            Log.i(ContentValues.TAG, "bitmapToMultipart: ${Base64.encodeToString(bitmapdata, Base64.NO_WRAP)}")
            val name: RequestBody = bitmapdata.toRequestBody("image/*".toMediaTypeOrNull(), 0, bitmapdata.size)
            val image = MultipartBody.Part.createFormData("myFile", "avatar.jpg", name)
            val response = repository.uploadImagePost(image = image, accessToken = accessToken, userId = userId)
            when(response){
                is Resource.Success -> {
                    jsonObject.put("url_media", response.data!!.url_media)
                }
                is Resource.Error -> {
                    return Resource.Error(response.message!!)
                }
            }

        }
        jsonObject.put("description", description)
        jsonObject.put("status", "New")
        jsonObject.put("price", price.toInt())
        //jsonObject.put("creation_time", time)
        val jsonObjectString = jsonObject.toString()
        val buuBodyRequest = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return repository.postCreateItem(userId, buuBodyRequest, "Bearer $accessToken")
    }

    suspend fun getUserPosts(
        userId: Int,
        accessToken: String
    ) {
        var result = repository.getUserPosts(userId, "Bearer $accessToken")
        when (result) {
            is Resource.Success -> {
                if (result.data!!.data != null) {
                    val postEntries = result.data!!.data.mapIndexed { index, entry ->
                        val creation_time: String = entry.creation_time
                        val description: String = entry.description
                        val url_media: String? = entry.url_media
                        val id_item = entry.id_item
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
                    listPosts.value += postEntries
                }
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                Log.e("Error: ", "Error ${loadError.value}")
                isLoading.value = false
            }
        }
    }


    suspend fun getUserItems(
        userId: Int,
        accessToken: String
    ) {
        val result = repository.getUserItems(userId, "Bearer $accessToken")
        when (result) {
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
                Log.e("Error: ", "loadItem - ${loadError.value}")
                isLoading.value = false
            }
        }
    }

    suspend fun postFollow(
        userId: Int,
        accessToken: String
    ): Resource<JsonStatus> {
        return repository.postFollow(userId = userId, accessToken = "Bearer $accessToken")
    }

    suspend fun postUnFollow(
        userId: Int,
        accessToken: String
    ): Resource<JsonStatus> {
        return repository.postUnFollow(userId = userId, accessToken = "Bearer $accessToken")
    }

    suspend fun getCheckFollow(
        userId: Int,
        accessToken: String
    ): Resource<JsonCheck> {
        return repository.getCheckFollow(userId = userId, accessToken = "Bearer $accessToken")
    }


    suspend fun uploadImage(
        imageBitmap: Bitmap,
        accessToken: String,
        UserId: Int
    ) {
        val bos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        Log.i(ContentValues.TAG, "bitmapToMultipart: ${Base64.encodeToString(bitmapdata, Base64.NO_WRAP)}")

        val name: RequestBody = bitmapdata.toRequestBody("image/*".toMediaTypeOrNull(), 0, bitmapdata.size)

        val image = MultipartBody.Part.createFormData("myFile", "avatar.jpg", name)

        repository.uploadImage(accessToken = accessToken,UserId = UserId, image = image )
    }

}