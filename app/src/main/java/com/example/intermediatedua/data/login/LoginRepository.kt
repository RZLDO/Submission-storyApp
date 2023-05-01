package com.example.intermediatedua.data.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService){
    private var _isLoading  = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun login(email: String,password: String ) : LoginResponse{
        _isLoading.value = true
        val response = loginService.login(email, password)
        _isLoading.value = false
        return response
    }
}