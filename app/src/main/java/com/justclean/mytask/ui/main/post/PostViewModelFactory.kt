package com.justclean.mytask.ui.main.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justclean.mytask.data.repo.PostRepo

@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(val repo: PostRepo) :ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repo) as T
    }
}