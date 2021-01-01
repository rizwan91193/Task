package com.justclean.mytask.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.justclean.mytask.data.db.entity.PostData

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(postData: List<PostData>)
    @Query("SELECT * FROM postData")
    fun getPostDataList():LiveData<List<PostData>>

}