package com.example.intermediatedua.presentation.registerScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.register.RegisterRepository
import com.example.intermediatedua.data.register.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository):ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResult : LiveData<RegisterResponse>
        get() = _registerResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun register(name:String, email : String, password: String){
        viewModelScope.launch {
            try {
                val response = registerRepository.registerUser(name, email, password)
                _registerResponse.value = response
            }catch (e: Exception){

            }
        }
        viewModelScope.launch {
            registerRepository.isLoading.observeForever {
                _isLoading.value = it
            }
        }

    }

}