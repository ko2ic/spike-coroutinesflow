package com.ko2ic.coroutinesflow.di

import com.ko2ic.coroutinesflow.common.repository.http.HttpClientCaller
import com.ko2ic.coroutinesflow.common.repository.http.HttpClientLocator
import com.ko2ic.coroutinesflow.model.annotation.HttpLocate
import com.ko2ic.coroutinesflow.model.valueobject.HttpLocateType
import com.ko2ic.coroutinesflow.repository.CommentRepository
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientDefault
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientErrorMock
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
    fun provideHttpClient() = HttpClientCaller()

    @Provides
    @Singleton
    fun provideHttpClientLocator(): HttpClientLocator = HttpClientDefault()

    @Provides
    @Singleton
    @HttpLocate(HttpLocateType.Github)
    fun provideLocatorError(): HttpClientLocator = HttpClientErrorMock()

    @Provides
    @Singleton
    fun provideCommentRepository(
        caller: HttpClientCaller,
        locator: HttpClientLocator,
        @HttpLocate(HttpLocateType.Github) errorLocator: HttpClientLocator
    ) = CommentRepository(caller, locator, errorLocator)

}

