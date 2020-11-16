package com.ko2ic.coroutinesflow.repository

import com.ko2ic.coroutinesflow.common.repository.http.HttpClientCaller
import com.ko2ic.coroutinesflow.common.repository.http.HttpClientLocator
import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import com.ko2ic.coroutinesflow.repository.http.CommentHttpClient
import kotlinx.coroutines.flow.Flow

class CommentRepository(
    private val caller: HttpClientCaller,
    private val locator: HttpClientLocator,
    private val errorLocator: HttpClientLocator
) {

    fun fetchComments(postId: Int): Flow<List<CommentEntity>?> {
        return caller.call<List<CommentEntity>> {
            val client = locator.lookup(CommentHttpClient::class.java)
            client.fetchComments(postId)
        }
    }

    fun error(): Flow<List<CommentEntity>?> {
        return caller.call<List<CommentEntity>> {
            val client = errorLocator.lookup(CommentHttpClient::class.java)
            client.fetchComments(0)
        }
    }
}