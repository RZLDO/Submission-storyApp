package com.example.intermediatedua.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertStory(Story: List<StoryResponseItems>)

    @Query("SELECT * FROM Story")
    fun getAllStory():PagingSource<Int, StoryResponseItems>

    @Query("DELETE FROM Story")
    suspend fun deleteAll()
}