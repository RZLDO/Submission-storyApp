package com.example.intermediatedua.presentation.loginScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.addStory.AddStoryRepository
import com.example.intermediatedua.data.login.LoginRepository
import com.example.intermediatedua.data.login.LoginResponse
import com.example.intermediatedua.data.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository):ViewModel() {
    sealed class LoginResult<out T: Any>{
        data class Success<out T: Any>(val data : T  ) : LoginResult<T>()
        data class Error(val error : String ): LoginResult<Nothing>()
    }

    val isLoading : LiveData<Boolean>
        get() = loginRepository.isLoading
    suspend fun login(email:String, password:String):LoginResult<LoginResponse>{
        return try{
            when(val result = loginRepository.login(email, password)){
                is LoginRepository.LoginResult.Success -> LoginResult.Success(result.data)
                is LoginRepository.LoginResult.Error -> LoginResult.Error(result.message)
            }
        }catch (e: Exception){
            LoginResult.Error(e.toString())
        }
    }
}