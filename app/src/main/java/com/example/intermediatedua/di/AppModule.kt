package com.example.intermediatedua.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.LocationManager
import android.util.Log
import androidx.room.Room
import com.example.intermediatedua.data.addStory.AddStoryService
import com.example.intermediatedua.data.detailStory.DetailStoryService
import com.example.intermediatedua.data.home.HomeService
import com.example.intermediatedua.data.local.UserPreferences.Companion.USER_TOKEN
import com.example.intermediatedua.data.local.room.StoryDatabase
import com.example.intermediatedua.data.login.LoginService
import com.example.intermediatedua.data.maps.MapsService
import com.example.intermediatedua.data.register.RegisterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BaseURL = "https://story-api.dicoding.dev/v1/"
    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences) :OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = sharedPreferences.getString(USER_TOKEN,"")
                Log.d("cekTokenInRetrofit", "$token")
                if (!token.isNullOrEmpty()){
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRegisterService(retrofit: Retrofit) : RegisterService{
        return retrofit.create(RegisterService::class.java)
    }
    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit) : LoginService{
        return retrofit.create(LoginService::class.java)
    }
    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit) : HomeService{
        return retrofit.create(HomeService::class.java)
    }

    @Provides
    @Singleton
    fun provideAddStoryService(retrofit: Retrofit) : AddStoryService{
        return retrofit.create(AddStoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailStoryService(retrofit: Retrofit) : DetailStoryService{
        return retrofit.create(DetailStoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideMapsService(retrofit: Retrofit) : MapsService{
        return retrofit.create(MapsService::class.java)
    }
}
@Module
@InstallIn(SingletonComponent::class)
object LocalModule{
    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
    }
    @Singleton
    @Provides
    fun provideStoryDatabase(application: Application): StoryDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            StoryDatabase::class.java,
            "story_database"
        ).build()
    }
}