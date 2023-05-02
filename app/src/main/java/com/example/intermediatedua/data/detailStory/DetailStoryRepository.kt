package com.example.intermediatedua.data.detailStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.intermediatedua.data.addStory.AddStoryResponse
import java.lang.Exception
import javax.inject.Inject

class DetailStoryRepository @Inject constructor(private val detailStoryService: DetailStoryService) {
    sealed class DetailResult<out T: Any >{
        data class Success<out T : Any>(val data : T ) : DetailResult<T>()
        data class Error(val error : String) : DetailResult<Nothing>()
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun detailResult(id: String ) : DetailResult<DetailStoryResponse>{
        return try {
            _isLoading.value = true
            val result = detailStoryService.storyDetail(id)
            _isLoading.value = false
            DetailResult.Success(result)
        }catch (e:Exception){
            _isLoading.value = false
            DetailResult.Error(e.toString())
        }
    }
}