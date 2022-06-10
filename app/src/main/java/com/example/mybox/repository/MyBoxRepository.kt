package com.example.mybox.repository

import com.example.mybox.data.remote.MyBoxApi
import com.example.mybox.data.remote.responses.*
import com.example.mybox.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.MultipartBody
import okhttp3.RequestBody

@ActivityScoped
class MyBoxRepository constructor(
    private val api: MyBoxApi
) {

    suspend fun postSignIn(
        requestBody: RequestBody
    ): Resource<JsonToken> {
        val response = try {
            api.postSignIn(requestBody)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postSignUp(
        requestBody: RequestBody
    ): Resource<JsonUserId> {
        val response = try {
            api.postCreateUser(requestBody)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }



    suspend fun postPing(
        accessToken: String
    ): Resource<JsonPing> {
        val response = try {
            api.postPing(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postUserId(
        accessToken: String
    ): Resource<JsonUserId> {
        val response = try {
            api.postUserId(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postCreatePost(
        userId: Int,
        requestBody: RequestBody,
        accessToken: String
    ): Resource<JsonPostId> {
        val response = try {
            api.postCreatePost(userId, requestBody, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postCreateItem(
        userId: Int,
        requestBody: RequestBody,
        accessToken: String
    ): Resource<JsonItemId> {
        val response = try {
            api.postCreateItem(userId, requestBody, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postFollow(
        userId: Int,
        accessToken: String
    ): Resource<JsonStatus> {
        val response = try {
            api.postFollow(userId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun uploadImage(
        image: MultipartBody.Part,
        accessToken: String,
        UserId: Int
    ) : Resource<JsonStatus> {
        val response = try {
            api.uploadImage("Bearer $accessToken", image, UserId)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun uploadImagePost(
        image: MultipartBody.Part,
        accessToken: String,
        userId: Int
    ) : Resource<JsonMediaUrl> {
        val response = try {
            api.uploadImagePost("Bearer $accessToken", image, userId)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun uploadImageItem(
        image: MultipartBody.Part,
        accessToken: String,
        userId: Int
    ) : Resource<JsonMediaUrl> {
        val response = try {
            api.uploadImageItem("Bearer $accessToken", image, userId)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postUnFollow(
        userId: Int,
        accessToken: String
    ): Resource<JsonStatus> {
        val response = try {
            api.postUnFollow(userId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getCheckFollow(
        userId: Int,
        accessToken: String
    ): Resource<JsonCheck> {
        val response = try {
            api.getCheckFollow(userId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }


    suspend fun getUser(
        userId: Int,
        accessToken: String
    ): Resource<JsonUser> {
        val response = try {
            api.getUser(userId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }


    suspend fun getItem(
        userId: Int,
        id_item: Int,
        accessToken: String
    ): Resource<JsonItem> {
        val response = try {
            api.getItem(userId,id_item,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postAddFavorite(
        userId: Int,
        id_item: Int,
        accessToken: String
    ): Resource<JsonStatus> {
        val response = try {
            api.postAddFavorite(userId,id_item,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postDeleteFavorite(
        userId: Int,
        id_item: Int,
        accessToken: String
    ): Resource<JsonStatus> {
        val response = try {
            api.postDeleteFavorite(userId,id_item,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getCheckFavorite(
        userId: Int,
        id_item: Int,
        accessToken: String
    ): Resource<JsonCheck> {
        val response = try {
            api.getCheckFavorite(userId,id_item,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getUserPosts(
        userId: Int,
        accessToken: String
    ): Resource<JsonUserPosts> {
        val response = try {
            api.getUserPosts(userId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getUserItems(
        userId: Int,
        accessToken: String
    ): Resource<JsonCatalog> {
        val response = try {
            api.getUserItems(userId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getLine(
        accessToken: String
    ): Resource<JsonUserPosts> {
        val response = try {
            api.getLine(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getFavorite(
        accessToken: String
    ): Resource<JsonCatalog> {
        val response = try {
            api.getFavorite(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getOrders(
        accessToken: String
    ): Resource<JsonOrders> {
        val response = try {
            api.getOrders(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getOrdersForYou(
        accessToken: String
    ): Resource<JsonOrders> {
        val response = try {
            api.getOrdersForYou(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getNotices(
        accessToken: String
    ): Resource<JsonNoticesList> {
        val response = try {
            api.getNotices(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getCheckNotices(
        accessToken: String
    ): Resource<JsonStatus> {
        val response = try {
            api.getCheckNotices(accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun postCreateOrder(
        requestBody: RequestBody,
        accessToken: String,
    ): Resource<JsonOrderId> {
        val response = try {
            api.postCreateOrder(requestBody,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun putOrderStatus(
        orderId: Int,
        status: String,
        accessToken: String
    ): Resource<JsonStatus> {
        val response = try {
            api.putOrderStatus(orderId,status,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
    suspend fun getChat(
        accessToken: String,
        chatId: Int,
    ): Resource<JsonChat> {
        val response = try {
            api.getChat(chatId, accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }


    suspend fun postSendMessage(
        chatID: Int,
        requestBody: RequestBody,
        accessToken: String
    ): Resource<JsonId> {
        val response = try {
            api.postSendMessage(chatID,requestBody,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getFindChatId1(
        orderId: Int,
        accessToken: String
    ): Resource<JsonId> {
        val response = try {
            api.getFindChatId1(orderId,accessToken)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}
