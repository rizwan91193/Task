package com.justclean.mytask.data.repo

import com.justclean.mytask.data.PostApi
import com.justclean.mytask.data.db.AppDatabase
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.data.network.PostDataResponse
import com.justclean.mytask.data.network.SafeApiRequest
import javax.inject.Inject

class PostRepo @Inject constructor(private val api:PostApi,private val db:AppDatabase):SafeApiRequest(){

    suspend fun getPostDataList():PostDataResponse{
        return apiRequest { api.getPostList() }
    }
    suspend fun insertPostDataList(postDataList:List<PostData>) = db.getPostDao().upsert(postDataList)

    fun getUser() = db.getPostDao().getPostDataList()


}