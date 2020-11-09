package com.ko2ic.coroutinesflow.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class CommentEntity(
    
    val postId: Long,

    val id: Long,
    val name: String,
    val email: String,
    val body: String
)