package com.example.intermediatedua.data.addStory

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AddStoryService {
    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part("description")description : RequestBody,
        @Part file : MultipartBody.Part,
        @Part("lat") lat: RequestBody,
        @Part("lat") long: RequestBody
    ) : AddStoryResponse
}