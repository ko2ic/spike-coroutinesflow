package com.ko2ic.coroutinesflow.repository

import com.ko2ic.coroutinesflow.common.repository.http.HttpClient
import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import com.ko2ic.coroutinesflow.repository.http.CommentHttpClient
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val httpClient: HttpClient) {

    fun fetchComments(postId: Int): Flow<List<CommentEntity>?> {
        return httpClient.call<List<CommentEntity>, CommentHttpClient> {
            it.fetchComments(postId)
        }
    }
}