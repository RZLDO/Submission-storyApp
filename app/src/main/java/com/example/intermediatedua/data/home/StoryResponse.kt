package com.example.intermediatedua.data.home

import com.example.intermediatedua.data.local.room.StoryResponseItems
import com.google.gson.annotations.SerializedName

data class StoryResponse(

	@field:SerializedName("listStory")
	val listStory: List<StoryResponseItems>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)