package com.justclean.mytask.ui.main.post

import android.telecom.Call
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.justclean.mytask.data.db.entity.DetailsData
import com.justclean.mytask.data.db.entity.FavoritesData
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.data.repo.PostRepo

class DetailViewModel @ViewModelInject constructor(val repo: PostRepo):ViewModel(){
    private val _index = MutableLiveData<Int>()
    val text: LiveData<Int> = Transformations.map(_index) {
        it
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    suspend fun getCommentList(id:Int):List<DetailsData> =repo.getCommentDataList(id)

    fun getPostDataById(id:Int) = repo.getPostListById(id)
    suspend fun insertToFavourites(favData: FavoritesData) = repo.insertToFavourites(favData)

}