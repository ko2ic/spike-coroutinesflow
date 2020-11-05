package com.ko2ic.coroutinesflow.ui.viewmodel

import androidx.databinding.ObservableField

data class CommentViewModel(val comment: Comment) : CollectionItemViewModel {
    val body = ObservableField("")

    init {
        this.body.set(comment.body)
    }
}