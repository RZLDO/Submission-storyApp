package com.example.intermediatedua.di

import android.content.SharedPreferences
import com.example.intermediatedua.data.addStory.AddStoryRepository
import com.example.intermediatedua.data.addStory.AddStoryService
import com.example.intermediatedua.data.detailStory.DetailStoryRepository
import com.example.intermediatedua.data.detailStory.DetailStoryService
import com.example.intermediatedua.data.home.HomeRepository
import com.example.intermediatedua.data.home.HomeService
import com.example.intermediatedua.data.local.UserPreferences
import com.example.intermediatedua.data.login.LoginRepository
import com.example.intermediatedua.data.login.LoginService
import com.example.intermediatedua.data.maps.MapsRepository
import com.example.intermediatedua.data.maps.MapsService
import com.example.intermediatedua.data.register.RegisterRepository
import com.example.intermediatedua.data.register.RegisterService
import com.example.intermediatedua.presentation.addStoryScreen.AddStoryViewModel
import com.example.intermediatedua.presentation.home.HomeViewModel
import com.example.intermediatedua.presentation.loginScreen.LoginViewModel
import com.example.intermediatedua.presentation.mapsScreen.MapsViewModel
import com.example.intermediatedua.presentation.registerScreen.RegisterViewModel
import com.example.intermediatedua.presentation.storyDetailScreen.DetailViewModel
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

    @Provides
    @Singleton
    fun provideHomeRepository(homeService: HomeService) = HomeRepository(homeService)

    @Provides
    @Singleton
    fun provideDetailRepository(detailStoryService: DetailStoryService) = DetailStoryRepository(detailStoryService)

    @Provides
    @Singleton
    fun provideAddStoryRepository(addStoryService: AddStoryService) = AddStoryRepository(addStoryService)

    @Provides
    @Singleton
    fun provideStoryCoordinatesRepository(mapsService: MapsService) = MapsRepository(mapsService)
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

    @Provides
    @Singleton
    fun provideHomeViewModel(homeRepository: HomeRepository) = HomeViewModel(homeRepository)

    @Provides
    @Singleton
    fun provideAddStoryViewModel(addStoryRepository: AddStoryRepository) = AddStoryViewModel(addStoryRepository)

    @Provides
    @Singleton
    fun provideDetailStoryViewModel(detailStoryRepository: DetailStoryRepository) = DetailViewModel(detailStoryRepository)

    @Provides
    @Singleton
    fun provideMapsViewModel(mapsRepository: MapsRepository) = MapsViewModel(mapsRepository)
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule{
    @Provides
    @Singleton
    fun provideUserPreferences(sharedPreferences: SharedPreferences) = UserPreferences(sharedPreferences)
}