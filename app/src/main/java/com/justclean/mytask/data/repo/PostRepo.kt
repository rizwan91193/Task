package com.justclean.mytask.data.repo

import com.justclean.mytask.data.PostApi
import com.justclean.mytask.data.db.AppDatabase
import com.justclean.mytask.data.db.entity.FavoritesData
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.data.network.PostDataResponse
import com.justclean.mytask.data.network.SafeApiRequest
import com.justclean.mytask.data.network.DetailDataResponse
import javax.inject.Inject

class PostRepo @Inject constructor(private val api:PostApi,private val db:AppDatabase):SafeApiRequest(){

    suspend fun getPostDataList():PostDataResponse{
        return apiRequest { api.getPostList() }
    }
    suspend fun insertPostDataList(postDataList:List<PostData>) = db.getPostDao().upsertAll(postDataList)

    fun getUser() = db.getPostDao().getPostDataList()

    suspend fun getCommentDataList(id:Int): DetailDataResponse {
        return apiRequest { api.getCommentList(id) }
    }

     fun getPostListById(id:Int) = db.getPostDao().getPostDataById(id)

    suspend fun insertToFavourites(favData: FavoritesData) = db.getPostDao().upsertFavData(favData)

    fun getFavDataList() = db.getPostDao().getFavDataList()

}