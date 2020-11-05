package com.ko2ic.coroutinesflow.model

data class Comment(
    val postId: Long,

    val id: Long,
    val name: String,
    val email: String,
    val body: String
)