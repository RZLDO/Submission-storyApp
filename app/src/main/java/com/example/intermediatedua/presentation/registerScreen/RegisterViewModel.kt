package com.example.intermediatedua.presentation.registerScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermediatedua.data.register.RegisterRepository
import com.example.intermediatedua.data.register.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository):ViewModel() {
    sealed class RegisterResult<out T: Any>{
        data class Success<out T : Any>(val data : T) : RegisterResult<T>()
        data class Error(val error : String) : RegisterResult<Nothing>()
    }

    val isLoading : LiveData<Boolean>
        get() = registerRepository.isLoading

    suspend fun register(name:String, email : String, password: String): RegisterResult<RegisterResponse>{
        return try {
            when(val response = registerRepository.registerUser(name, email, password)){
                is RegisterRepository.RegisterResult.Success -> RegisterResult.Success(response.data)
                is RegisterRepository.RegisterResult.Error -> RegisterResult.Error(response.message)
            }
        }catch (e:Exception){
            RegisterResult.Error(e.toString())
        }

    }

}