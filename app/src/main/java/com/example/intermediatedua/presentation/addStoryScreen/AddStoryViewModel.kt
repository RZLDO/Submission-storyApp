package com.example.intermediatedua.presentation.addStoryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.addStory.AddStoryRepository
import com.example.intermediatedua.data.addStory.AddStoryResponse
import com.example.intermediatedua.presentation.storyDetailScreen.DetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
@HiltViewModel
class AddStoryViewModel @Inject constructor(private val addStoryRepository: AddStoryRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    sealed class AddStoryResult <out T : Any>{
        data class Success<out T : Any>(val data : T) : AddStoryResult<T>()
        data class Error(val message: String) : AddStoryResult<Nothing>()
    }

    suspend fun addStory(description: RequestBody, file: MultipartBody.Part) : AddStoryResult<AddStoryResponse>{
        _isLoading.value = true
        return try {
            val response = addStoryRepository.addStory(description, file)
            _isLoading.value = false
            when(response) {

                is AddStoryRepository.Result.Success -> AddStoryResult.Success(response.data)
                is AddStoryRepository.Result.Error -> AddStoryResult.Error(response.message)
            }
        }catch (e: Exception){
            _isLoading.value = false
            AddStoryResult.Error(e.toString())
        }
    }
}