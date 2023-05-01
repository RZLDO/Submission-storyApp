package com.example.intermediatedua.data.register

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {
    @FormUrlEncoded
    @POST("register")
    suspend fun registerUsers(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("password")password :String
    ) : RegisterResponse
}