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

    suspend fun addStory(description : RequestBody, file : MultipartBody.Part) : Result<AddStoryResponse>{
        return try{
            val response = addStoryService.addStory(description, file,)
            Result.Success(response)
        }catch (e: Exception){
            Result.Error(e.toString())
        }
    }
}