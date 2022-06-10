package com.example.mybox.data.remote

import com.example.mybox.data.remote.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MyBoxApi {

    @POST("auth/sign-in")
    suspend fun postSignIn(
        @Body requestBody: RequestBody
    ) : JsonToken

    @POST("auth/sign-up")
    suspend fun postCreateUser(
        @Body requestBody: RequestBody
    ) : JsonUserId

    @POST("api/ping")
    suspend fun postPing(
        @Header("Authorization") authorization : String
    ) : JsonPing

    @POST("api/{id}/posts")
    suspend fun postCreatePost(
        @Path("id") userId:Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") authorization : String
    ) : JsonPostId

    @POST("api/{id}/catalog")
    suspend fun postCreateItem(
        @Path("id") userId:Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") authorization : String
    ) : JsonItemId

    @POST("api/{id}/follow")
    suspend fun postFollow(
        @Path("id") userId:Int,
        @Header("Authorization") authorization : String
    ) : JsonStatus

    @POST("api/{id}/unfollow")
    suspend fun postUnFollow(
        @Path("id") userId:Int,
        @Header("Authorization") authorization : String
    ) : JsonStatus

    @Multipart
    @POST("api/{id}/upload_avatar")
    suspend fun uploadImage(
        @Header("Authorization") authorization : String,
        @Part file: MultipartBody.Part?,
        @Path("id") userId:Int,
    ): JsonStatus

    @Multipart
    @POST("api/{id}/posts/upload_post_media")
    suspend fun uploadImagePost(
        @Header("Authorization") authorization : String,
        @Part file: MultipartBody.Part?,
        @Path("id") userId:Int,
    ): JsonMediaUrl

    @Multipart
    @POST("api/{id}/upload_item_media")
    suspend fun uploadImageItem(
        @Header("Authorization") authorization : String,
        @Part file: MultipartBody.Part?,
        @Path("id") userId:Int,
    ): JsonMediaUrl


    @GET("api/{id}/check_follow")
    suspend fun getCheckFollow(
        @Path("id") userId:Int,
        @Header("Authorization") authorization : String
    ) : JsonCheck

    @GET("api/get-id")
    suspend fun postUserId(
        @Header("Authorization") authorization : String
    ) : JsonUserId

    @GET("api/{id}")
    suspend fun getUser(
        @Path("id") userId:Int,
        @Header("Authorization") authorization : String
    ) : JsonUser

    @GET("api/{id}/catalog/{id_item}")
    suspend fun getItem(
        @Path("id") userId:Int,
        @Path("id_item") id_item:Int,
        @Header("Authorization") authorization : String
    ) : JsonItem

    @GET("api/{id}/favorite/{id_item}/check")
    suspend fun getCheckFavorite(
        @Path("id") userId:Int,
        @Path("id_item") id_item:Int,
        @Header("Authorization") authorization : String
    ) : JsonCheck

    @POST("api/{id}/favorite/{id_item}")
    suspend fun postAddFavorite(
        @Path("id") userId:Int,
        @Path("id_item") id_item:Int,
        @Header("Authorization") authorization : String
    ) : JsonStatus

    @POST("api/{id}/unfavorite/{id_item}")
    suspend fun postDeleteFavorite(
        @Path("id") userId:Int,
        @Path("id_item") id_item:Int,
        @Header("Authorization") authorization : String
    ) : JsonStatus

    @GET("api/{id}/posts")
    suspend fun getUserPosts(
        @Path("id") userId:Int,
        @Header("Authorization") authorization : String
    ) : JsonUserPosts

    @GET("api/{id}/catalog")
    suspend fun getUserItems(
        @Path("id") userId:Int,
        @Header("Authorization") authorization : String
    ) : JsonCatalog

    @GET("api/line")
    suspend fun getLine(
        @Header("Authorization") authorization : String
    ) : JsonUserPosts


    @GET("api/favorite")
    suspend fun getFavorite(
        @Header("Authorization") authorization : String
    ) : JsonCatalog


    @POST("api/order")
    suspend fun postCreateOrder(
        @Body requestBody: RequestBody,
        @Header("Authorization") authorization : String
    ) : JsonOrderId


    @GET("api/notice")
    suspend fun getNotices(
        @Header("Authorization") authorization : String
    ) : JsonNoticesList

    @GET("api/notice/check")
    suspend fun getCheckNotices(
        @Header("Authorization") authorization : String
    ) : JsonStatus

    @GET("api/order")
    suspend fun getOrders(
        @Header("Authorization") authorization : String
    ) : JsonOrders

    @GET("api/order/shop")
    suspend fun getOrdersForYou(
        @Header("Authorization") authorization : String
    ) : JsonOrders

    @PUT("api/order/{id}/{status}")
    suspend fun putOrderStatus(
        @Path("id") orderId:Int,
        @Path("status") status:String,
        @Header("Authorization") authorization : String
    ) : JsonStatus


    @GET("api/chat/{id}")
    suspend fun getChat(
        @Path("id") chatId:Int,
        @Header("Authorization") authorization : String
    ) : JsonChat

    @POST("api/chat/{id}")
    suspend fun postSendMessage(
        @Path("id") chatId:Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") authorization : String
    ) : JsonId

    @GET("api/chat/find/{id}")
    suspend fun getFindChatId1(
        @Path("id") orderId:Int,
        @Header("Authorization") authorization : String
    ) : JsonId
}