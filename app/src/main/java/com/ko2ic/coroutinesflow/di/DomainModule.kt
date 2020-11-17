package com.ko2ic.coroutinesflow.di

import com.ko2ic.coroutinesflow.CommentRepository
import com.ko2ic.coroutinesflow.model.Comment
import com.ko2ic.coroutinesflow.model.annotation.HttpClientStrategy
import com.ko2ic.coroutinesflow.model.valueobject.HttpClientStrategyType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideComment(
        @HttpClientStrategy(HttpClientStrategyType.Retrofit) retrofitCommentRepository: CommentRepository,
        @HttpClientStrategy(HttpClientStrategyType.Ktor) ktorCommentRepository: CommentRepository,
    ) = Comment(retrofitCommentRepository, ktorCommentRepository)

}

