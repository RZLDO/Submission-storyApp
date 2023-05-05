package com.example.intermediatedua.data.home

import com.example.intermediatedua.data.local.room.StoryResponseItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("stories")
    suspend fun fetchStory(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

}