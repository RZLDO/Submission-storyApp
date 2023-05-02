package com.example.intermediatedua.presentation.storyDetailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intermediatedua.data.detailStory.DetailStoryRepository
import com.example.intermediatedua.data.detailStory.DetailStoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel


class DetailViewModel @Inject constructor(private val detailStoryRepository: DetailStoryRepository):ViewModel() {
    sealed class DetailResult<out T : Any>{
        data class Success<out T: Any>(val data : T) : DetailResult<T>()
        data class Error(val error : String) : DetailResult<Nothing>()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun detailStory(id:String) : DetailResult<DetailStoryResponse>?{
        try {
            return try {
                _isLoading.value = true
                val result = detailStoryRepository.detailResult(id)
                _isLoading.value = false
                when (result) {
                    is DetailStoryRepository.DetailResult.Success -> DetailResult.Success(result.data)
                    is DetailStoryRepository.DetailResult.Error -> DetailResult.Error(result.error)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                null
            }
        }catch (e:Exception){
            _isLoading.value = false
            return DetailResult.Error(e.toString())
        }
    }
}