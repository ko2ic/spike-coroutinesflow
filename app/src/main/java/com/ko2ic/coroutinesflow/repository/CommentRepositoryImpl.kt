package com.ko2ic.coroutinesflow.repository

import com.ko2ic.coroutinesflow.CommentRepository
import com.ko2ic.coroutinesflow.common.repository.http.HttpClientCaller
import com.ko2ic.coroutinesflow.common.repository.http.HttpClientLocator
import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import com.ko2ic.coroutinesflow.repository.http.CommentHttpClient
import com.ko2ic.coroutinesflow.repository.http.KtorCommentHttpClient
import kotlinx.coroutines.flow.Flow

class CommentRepositoryImpl(
    private val caller: HttpClientCaller,
    private val locator: HttpClientLocator,
    private val errorLocator: HttpClientLocator
) : CommentRepository {

    override fun fetchComments(postId: Int): Flow<List<CommentEntity>?> {
        return caller.call<List<CommentEntity>> {
            val client = locator.lookup(CommentHttpClient::class.java)
            client.fetchComments(postId)
        }
    }

    override fun error(): Flow<List<CommentEntity>?> {
        return caller.call<List<CommentEntity>> {
            val client = errorLocator.lookup(CommentHttpClient::class.java)
            client.fetchComments(0)
        }
    }
}

class KtorCommentRepositoryImpl(
    private val caller: HttpClientCaller,
    private val client: KtorCommentHttpClient
) : CommentRepository {

    override fun fetchComments(postId: Int): Flow<List<CommentEntity>?> {
        return caller.call<List<CommentEntity>> {
            client.fetchComments(postId)
        }
    }

    override fun error(): Flow<List<CommentEntity>?> {
        return caller.call<List<CommentEntity>> {
            client.error()
        }
    }
}