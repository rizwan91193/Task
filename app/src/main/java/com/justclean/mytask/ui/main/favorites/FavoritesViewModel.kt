package com.justclean.mytask.ui.main.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.justclean.mytask.data.repo.PostRepo

class FavoritesViewModel @ViewModelInject constructor(val repo: PostRepo):ViewModel(){
    fun getFavDataList() = repo.getFavDataList()
}