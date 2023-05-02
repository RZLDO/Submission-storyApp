package com.example.intermediatedua.data.home

import retrofit2.Call
import retrofit2.http.GET

interface HomeService {
    @GET("stories")
    suspend fun fetchStory(): StoryResponse
}