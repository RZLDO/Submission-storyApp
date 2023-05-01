package com.example.intermediatedua.data.local

import android.content.SharedPreferences
import androidx.core.content.edit


class UserPreferences(private val sharedPreferences: SharedPreferences) {
    companion object{
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
        const val USER_TOKEN = "user token"
    }

    fun saveUser(userModel: UserModel){
        sharedPreferences.edit{
            putString(USER_ID, userModel.userId)
            putString(USER_NAME, userModel.name)
            putString(USER_TOKEN, userModel.token)
        }
    }

    fun getUser():UserModel?{
        val userId = sharedPreferences.getString(USER_ID,null)
        val name = sharedPreferences.getString(USER_NAME, null)
        val token = sharedPreferences.getString(USER_TOKEN, null)
        return if (userId != null && name != null && token != null){
            UserModel(userId,name,token)

        }else{
            null
        }
    }
    fun clearUser(){
        sharedPreferences.edit {
            remove(USER_ID)
            remove(USER_NAME)
            remove(USER_TOKEN)
        }
    }
}