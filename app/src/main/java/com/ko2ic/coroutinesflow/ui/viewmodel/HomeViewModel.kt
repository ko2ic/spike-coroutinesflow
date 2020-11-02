package com.ko2ic.coroutinesflow.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val viewModels = ObservableArrayList<CommentViewModel>()

    private val _list = MutableLiveData<List<CommentViewModel>>().apply {
        value = listOf(
            CommentViewModel(
                Comment(
                    1,
                    1,
                    "name1-1",
                    "name1-1@gmail.com",
                    "body1-1"
                )
            ),
            CommentViewModel(
                Comment(
                    1,
                    2,
                    "name1-2",
                    "name1-2@gmail.com",
                    "body1-2"
                )
            ),
            CommentViewModel(
                Comment(
                    1,
                    3,
                    "name1-3",
                    "name1-3@gmail.com",
                    "body1-3"
                )
            )
        )
    }
    val list: LiveData<List<CommentViewModel>> = _list

    fun render(itemViewModels: List<CommentViewModel>) {
        viewModels.clear()
        viewModels.addAll(itemViewModels)
    }
}

data class CommentViewModel(val comment: Comment) : CollectionItemViewModel {
    val body = ObservableField("")

    init {
        this.body.set(comment.body)
    }
}

data class Comment(
    val postId: Long,

    val id: Long,
    val name: String,
    val email: String,
    val body: String
)