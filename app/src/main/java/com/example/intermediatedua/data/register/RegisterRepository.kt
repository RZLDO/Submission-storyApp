package com.example.intermediatedua.data.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val registerService:RegisterService){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): RegisterResponse {
        _isLoading.value = true
        val response = registerService.registerUsers(name, email, password)
        _isLoading.value = false
        return response
    }
}