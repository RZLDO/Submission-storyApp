package com.example.intermediatedua.presentation.addStoryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.addStory.AddStoryRepository
import com.example.intermediatedua.data.addStory.AddStoryResponse
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

    sealed class AddStoryResult {
        data class Success(val response: AddStoryResponse) : AddStoryResult()
        data class Error(val message: String) : AddStoryResult()
    }

    private val _addStoryResult = MutableLiveData<AddStoryResult>()
    val addStoryResult: LiveData<AddStoryResult>
        get() = _addStoryResult

    fun addStory(description: RequestBody, file: MultipartBody.Part, lat: RequestBody, long: RequestBody) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = addStoryRepository.addStory(description, file, lat, long)
            _isLoading.value = false
            when (result) {
                is AddStoryRepository.Result.Success -> {
                    _addStoryResult.value = AddStoryResult.Success(result.data)
                }
                is AddStoryRepository.Result.Error -> {
                    _addStoryResult.value = AddStoryResult.Error(result.message)
                }
            }
        }
    }
}