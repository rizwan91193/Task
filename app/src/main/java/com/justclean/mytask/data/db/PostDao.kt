package com.justclean.mytask.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.justclean.mytask.data.db.entity.FavoritesData
import com.justclean.mytask.data.db.entity.PostData

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(postData: List<PostData>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavData(favData:FavoritesData)
    @Query("SELECT * FROM postData")
    fun getPostDataList():LiveData<List<PostData>>
    @Query("SELECT * FROM favorites")
    fun getFavDataList():LiveData<List<FavoritesData>>
    @Query("SELECT * FROM postData WHERE id =:postId")
    fun getPostDataById(postId:Int):LiveData<PostData>

}