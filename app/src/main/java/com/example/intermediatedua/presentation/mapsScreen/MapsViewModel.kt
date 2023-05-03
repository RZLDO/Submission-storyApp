package com.example.intermediatedua.presentation.mapsScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.intermediatedua.data.home.StoryResponse
import com.example.intermediatedua.data.maps.MapsRepository

class MapsViewModel(private val mapsRepository: MapsRepository): ViewModel() {
    sealed class MapsResult<out T: Any>{
        data class Success<out T : Any>(val data : T) : MapsResult<T>()
        data class Error(val message : String ): MapsResult<Nothing>()
    }
    val isLoading : LiveData<Boolean>
        get() = mapsRepository.isLoading
    suspend fun getLatLong() : MapsResult<StoryResponse>{
        return when(val result = mapsRepository.getStoryWithCoordinates()){
            is MapsRepository.MapsResponse.Success -> MapsResult.Success(result.data)
            is MapsRepository.MapsResponse.Error -> MapsResult.Error(result.message)
        }
    }
}