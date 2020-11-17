package com.ko2ic.coroutinesflow.di

import com.ko2ic.coroutinesflow.CommentRepository
import com.ko2ic.coroutinesflow.common.repository.http.HttpClientCaller
import com.ko2ic.coroutinesflow.common.repository.http.HttpClientLocator
import com.ko2ic.coroutinesflow.model.annotation.HttpClientStrategy
import com.ko2ic.coroutinesflow.model.annotation.HttpLocate
import com.ko2ic.coroutinesflow.model.valueobject.HttpClientStrategyType
import com.ko2ic.coroutinesflow.model.valueobject.HttpLocateType
import com.ko2ic.coroutinesflow.repository.CommentRepositoryImpl
import com.ko2ic.coroutinesflow.repository.KtorCommentRepositoryImpl
import com.ko2ic.coroutinesflow.repository.http.KtorCommentHttpClient
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientDefault
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientErrorMock
import com.ko2ic.coroutinesflow.repository.http.common.KtorHttpClientDefault
import com.ko2ic.coroutinesflow.repository.http.common.KtorHttpClientErrorMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class InfrastructuresModule {

    @Provides
    @Singleton
    fun provideHttpClientLocator(): HttpClientLocator = HttpClientDefault()

    @Provides
    @Singleton
    @HttpLocate(HttpLocateType.Github)
    fun provideLocatorError(): HttpClientLocator = HttpClientErrorMock()

    @Provides
    @Singleton
    @HttpClientStrategy(HttpClientStrategyType.Retrofit)
    fun provideCommentRepository(
        caller: HttpClientCaller,
        locator: HttpClientLocator,
        @HttpLocate(HttpLocateType.Github) errorLocator: HttpClientLocator
    ): CommentRepository = CommentRepositoryImpl(caller, locator, errorLocator)

    @Provides
    @Singleton
    @HttpClientStrategy(HttpClientStrategyType.Ktor)
    fun provideKtorCommentRepository(
        caller: HttpClientCaller,
        client: KtorCommentHttpClient
    ): CommentRepository = KtorCommentRepositoryImpl(caller, client)

    @Provides
    @Singleton
    fun provideKtorCommentHttpClient(
        client: KtorHttpClientDefault,
        errorClient: KtorHttpClientErrorMock
    ) = KtorCommentHttpClient(client, errorClient)
}

