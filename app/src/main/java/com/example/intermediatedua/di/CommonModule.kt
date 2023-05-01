package com.example.intermediatedua.di

import android.content.SharedPreferences
import com.example.intermediatedua.data.local.UserPreferences
import com.example.intermediatedua.data.login.LoginRepository
import com.example.intermediatedua.data.login.LoginService
import com.example.intermediatedua.data.register.RegisterRepository
import com.example.intermediatedua.data.register.RegisterService
import com.example.intermediatedua.presentation.loginScreen.LoginViewModel
import com.example.intermediatedua.presentation.registerScreen.RegisterViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{
    @Provides
    @Singleton
    fun provideRegisterRepository(registerService: RegisterService) = RegisterRepository(registerService)
    @Provides
    @Singleton
    fun provideLoginRepository(loginService: LoginService) = LoginRepository(loginService)
}
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule{
    @Provides
    @Singleton
    fun provideRegisterViewModel(registerRepository: RegisterRepository) = RegisterViewModel(registerRepository)

    @Provides
    @Singleton
    fun provideLoginViewModel(loginRepository: LoginRepository) = LoginViewModel(loginRepository)
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule{
    @Provides
    @Singleton
    fun provideUserPreferences(sharedPreferences: SharedPreferences) = UserPreferences(sharedPreferences)
}