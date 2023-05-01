package com.example.intermediatedua.presentation.loginScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.login.LoginRepository
import com.example.intermediatedua.data.login.LoginResponse
import com.example.intermediatedua.data.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository):ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult : LiveData<LoginResponse>
        get() = _loginResult
    suspend fun login(email : String , password : String){
        viewModelScope.launch {
            val response = loginRepository.login(email, password)
            _loginResult.value = response
        }
        viewModelScope.launch {
            loginRepository.isLoading.observeForever {
                _isLoading.value = it
            }
        }
    }
}