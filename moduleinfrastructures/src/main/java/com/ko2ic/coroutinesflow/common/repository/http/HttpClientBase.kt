package com.ko2ic.coroutinesflow.common.repository.http

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ko2ic.coroutinesflow.common.model.enums.HttpErrorType
import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset

abstract class HttpClientBase : com.ko2ic.coroutinesflow.common.repository.http.HttpClientLocator {

    @ExperimentalSerializationApi
    private val retrofit by lazy {
        val httpClient =
            com.ko2ic.coroutinesflow.common.repository.http.OkHttpClientSingleton.instance.httpClient
        val builder = this.addInterceptor(httpClient.newBuilder())

        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(format.asConverterFactory(contentType))
            .client(builder.build())
            .build()
    }

    @ExperimentalSerializationApi
    override fun <T> lookup(clazz: Class<T>): T = retrofit.create(clazz)

    abstract val baseUrl: String

    // 継承先で処理を入れたい時にoverrideする
    open fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder = builder

    companion object {
        suspend fun asHttpErrorTypeException(throwable: Throwable) = when (throwable) {
            is HttpException -> {
                // Retrofit用
                val excption = throwable
                try {
                    val json = excption.response()?.errorBody()?.string()

                    HttpErrorTypeException(
                        HttpErrorType.StatusCode(
                            excption.code(),
                            json,
                            throwable
                        )
                    )

                } catch (e: Exception) {
                    HttpErrorTypeException(HttpErrorType.Unknown(throwable))
                }
            }
            is ResponseException -> {
                // Ktor用
                val response = throwable.response
                val json = response.readText(Charset.forName("utf-8"))
                HttpErrorTypeException(
                    HttpErrorType.StatusCode(
                        response.status.value,
                        json,
                        throwable
                    )
                )


            }
            is SocketTimeoutException -> {
                HttpErrorTypeException(HttpErrorType.TimedoutError(throwable))
            }
            is UnknownHostException, is NoRouteToHostException -> {
                HttpErrorTypeException(HttpErrorType.CannotFindHost(throwable))
            }
            is ConnectException -> {
                HttpErrorTypeException(HttpErrorType.CannotConnectToHost(throwable))
            }
            else -> {
                HttpErrorTypeException(HttpErrorType.Unknown(throwable))
            }
        }
    }

}