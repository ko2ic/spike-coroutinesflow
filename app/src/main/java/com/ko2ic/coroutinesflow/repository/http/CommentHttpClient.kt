package com.ko2ic.coroutinesflow.repository.http

import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentHttpClient {

    @GET("comments")
    suspend fun fetchComments(@Query("postId") postId: Int): List<CommentEntity>

    @GET("error")
    suspend fun error(): List<CommentEntity>

}