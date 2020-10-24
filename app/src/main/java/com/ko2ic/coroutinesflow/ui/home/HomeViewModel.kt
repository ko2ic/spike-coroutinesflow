package com.ko2ic.coroutinesflow.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Comment>>().apply {
        value = listOf(
            Comment(1, 1, "name1-1", "name1-1@gmail.com", "body1-1"),
            Comment(1, 2, "name1-2", "name1-2@gmail.com", "body1-2"),
            Comment(1, 3, "name1-3", "name1-3@gmail.com", "body1-3")
        )
    }
    val list: LiveData<List<Comment>> = _list
}

data class Comment(
    val postId: Long,

    val id: Long,
    val name: String,
    val email: String,
    val body: String
)