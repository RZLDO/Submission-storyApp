package com.example.intermediatedua.data.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService){
    sealed class LoginResult<out T: Any>{
        data class Success<out T:Any >(val data : T ) : LoginResult<T>()
        data class Error(val message : String ): LoginResult<Nothing>()
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    suspend fun login(email: String,password: String ) : LoginResult<LoginResponse>{
        _isLoading.value = true
        return try{
            val response = loginService.login(email, password)
            _isLoading.value = false
            LoginResult.Success(response)
        }catch (e: Exception){
            _isLoading.value = false
            LoginResult.Error("message :${e.message}")
        }
    }
}