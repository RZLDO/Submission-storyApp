package com.example.intermediatedua.data.detailStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.intermediatedua.data.addStory.AddStoryResponse
import com.example.intermediatedua.data.home.StoryResponse
import java.lang.Exception
import javax.inject.Inject

class DetailStoryRepository @Inject constructor(private val detailStoryService: DetailStoryService) {
    sealed class DetailResult<out T: Any >{
        data class Success<out T : Any>(val data : T ) : DetailResult<T>()
        data class Error(val error : String) : DetailResult<Nothing>()
    }


    suspend fun detailResult(id: String ) :  DetailResult<DetailStoryResponse>{
        return try {
            val result = detailStoryService.storyDetail(id)
            DetailResult.Success(result)
        }catch (e:Exception){
            DetailResult.Error(e.toString())
        }
    }
}