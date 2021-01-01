package com.justclean.mytask.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel @ViewModelInject constructor() : ViewModel() {

        private val _index = MutableLiveData<Int>()
        val text: LiveData<String> = Transformations.map(_index) {
            "$it"
        }

        fun setIndex(index: Int) {
            _index.value = index
        }
}