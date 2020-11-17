package com.ko2ic.coroutinesflow.model

import com.ko2ic.coroutinesflow.CommentRepository
import com.ko2ic.coroutinesflow.model.entity.CommentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// ドメインサービスや集約的なロジックはここに書く
// 状態はインスタンスとしては持たせないが、DBやメモリから取ってくる。
// 他のドメインとも協調させる
// ここからrepositoryを呼ぶ
class Comment @Inject constructor(
    private val commentRepository: CommentRepository,
    private val ktorCommentRepository: CommentRepository
) {

    fun fetchComments(postId: Int): Flow<List<CommentEntity>?> =
        commentRepository.fetchComments(postId)

    fun error(): Flow<List<CommentEntity>?> = commentRepository.error()

    fun fetchCommentsByKtor(postId: Int): Flow<List<CommentEntity>?> =
        ktorCommentRepository.fetchComments(postId)

    fun errorByKtor(): Flow<List<CommentEntity>?> = ktorCommentRepository.error()
}