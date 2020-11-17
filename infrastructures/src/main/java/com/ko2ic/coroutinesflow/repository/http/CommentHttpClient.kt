package com.ko2ic.coroutinesflow.repository.http

import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import com.ko2ic.coroutinesflow.repository.http.common.KtorHttpClientDefault
import com.ko2ic.coroutinesflow.repository.http.common.KtorHttpClientErrorMock
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentHttpClient {

    @GET("comments")
    suspend fun fetchComments(@Query("postId") postId: Int): List<CommentEntity>

    @GET("error")
    suspend fun error(): List<CommentEntity>

}

class KtorCommentHttpClient(
    private val client: KtorHttpClientDefault,
    private val errorClient: KtorHttpClientErrorMock
) {

    suspend fun fetchComments(postId: Int): List<CommentEntity> {
        return client.get<List<CommentEntity>>("/comments", mutableMapOf("postId" to postId))
    }

    suspend fun error(): List<CommentEntity> {
        return errorClient.get<List<CommentEntity>>("/comments", mutableMapOf())
    }
}