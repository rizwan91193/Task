package com.justclean.mytask.ui.main.post

import androidx.lifecycle.ViewModel
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.data.network.PostDataResponse
import com.justclean.mytask.data.repo.PostRepo
import javax.inject.Inject
import androidx.hilt.lifecycle.ViewModelInject

class PostViewModel @ViewModelInject constructor(val repo:PostRepo) :ViewModel(){

   fun getPostDataList() = repo.getUser()

    suspend fun getPostList():PostDataResponse = repo.getPostDataList()
    suspend fun insertPostDataList(postDataList:List<PostData>) = repo.insertPostDataList(postDataList)


}