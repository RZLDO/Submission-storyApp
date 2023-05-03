package com.example.intermediatedua.data.maps

import com.example.intermediatedua.data.home.StoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsService {
    @GET("stories")
    suspend fun fetchStoryWithCoordinates(@Query("location")location : String) : StoryResponse
}