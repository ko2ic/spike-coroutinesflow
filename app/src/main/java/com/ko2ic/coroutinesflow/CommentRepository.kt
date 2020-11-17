package com.ko2ic.coroutinesflow

import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun fetchComments(postId: Int): Flow<List<CommentEntity>?>
    fun error(): Flow<List<CommentEntity>?>
}