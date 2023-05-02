package com.example.intermediatedua.data.detailStory

import retrofit2.http.GET
import retrofit2.http.Path


interface DetailStoryService {
    @GET("stories/{id}")
    suspend fun storyDetail(@Path("id")id:String): DetailStoryResponse
}