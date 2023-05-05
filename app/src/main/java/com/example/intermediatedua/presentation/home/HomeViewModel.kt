package com.example.intermediatedua.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.intermediatedua.data.home.HomeRepository
import com.example.intermediatedua.data.home.ListStoryItem
import com.example.intermediatedua.data.home.StoryResponse
import com.example.intermediatedua.data.local.room.StoryResponseItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(homeRepository: HomeRepository):ViewModel() {
    val story: LiveData<PagingData<StoryResponseItems>> =
        homeRepository.getStory().cachedIn(viewModelScope)
}