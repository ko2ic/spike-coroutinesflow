package com.ko2ic.coroutinesflow.di

import com.ko2ic.coroutinesflow.common.repository.http.HttpClient
import com.ko2ic.coroutinesflow.repository.CommentRepository
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientDefault
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
    fun provideHttpClient(locator: HttpClientDefault) = HttpClient(locator)

    @Provides
    @Singleton
    fun provideHttpClientLocator() = HttpClientDefault()

    @Provides
    @Singleton
    fun proviceCommentRepository(client: HttpClient) = CommentRepository(client)

}

