package com.example.intermediatedua.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.home.HomeRepository
import com.example.intermediatedua.data.home.ListStoryItem
import com.example.intermediatedua.data.home.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository):ViewModel() {
    private val _storyResponse = MutableLiveData<List<ListStoryItem>>()
    val storyResponse : LiveData<List<ListStoryItem>>
        get() = _storyResponse
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun fetchStory(){
        viewModelScope.launch {
            val response = homeRepository.fetchStory()
            _storyResponse.value = response.listStory
        }
        viewModelScope.launch {
            homeRepository.isLoading.observeForever {
                _isLoading.value = it
            }
        }
    }
}