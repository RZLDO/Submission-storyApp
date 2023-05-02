package com.example.intermediatedua.data.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class HomeRepository @Inject constructor(private val homeService: HomeService){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun fetchStory() : StoryResponse{
        _isLoading.value = true
        val response = homeService.fetchStory()
        _isLoading.value = false
        return  response
    }
}