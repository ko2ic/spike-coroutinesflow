package com.ko2ic.coroutinesflow.ui.viewmodel

import androidx.databinding.ObservableField
import com.ko2ic.coroutinesflow.model.entity.CommentEntity

data class CommentViewModel(val comment: CommentEntity) : CollectionItemViewModel {
    val email = ObservableField("")

    init {
        this.email.set(comment.email)
    }
}