package com.example.intermediatedua.data.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception
import javax.inject.Inject

class AddStoryRepository @Inject constructor(private val addStoryService: AddStoryService) {
    sealed class Result<out T : Any>{
        data class Success<out T : Any>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading
    suspend fun addStory(description : RequestBody, file : MultipartBody.Part , lat : RequestBody, long : RequestBody) : Result<AddStoryResponse>{
        return try{
            _isLoading.value = true
            val response = addStoryService.addStory(description, file,lat, long)
            _isLoading.value = false
            Result.Success(response)
        }catch (e: Exception){
            _isLoading.value = false
            Result.Error(e.toString())
        }
    }
}