package com.example.intermediatedua.data.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val registerService:RegisterService){
    sealed class RegisterResult<out T: Any>{
        data class Success<out T:Any>(val data : T ): RegisterResult<T>()
        data class Error(val message: String ): RegisterResult<Nothing>()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): RegisterResult<RegisterResponse> {
        _isLoading.value = true
        return try {
            val response = registerService.registerUsers(name, email, password)
            _isLoading.value = false
            RegisterResult.Success(response)
        }catch (e: Exception){
            _isLoading.value = false
            RegisterResult.Error(e.toString())
        }
    }
}