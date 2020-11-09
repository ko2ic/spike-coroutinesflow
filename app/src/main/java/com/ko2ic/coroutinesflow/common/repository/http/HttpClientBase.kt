package com.ko2ic.coroutinesflow.common.repository.http

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import com.ko2ic.coroutinesflow.common.model.valueobject.enums.HttpErrorType
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class HttpClientBase : HttpClientLocator {

    private val retrofit by lazy {
        val httpClient = OkHttpClientSingleton.instance.httpClient
        val builder = this.addInterceptor(httpClient.newBuilder())

        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(format.asConverterFactory(contentType))
            .client(builder.build())
            .build()
    }

    override fun <T> lookup(clazz: Class<T>): T = retrofit.create(clazz)

    abstract val baseUrl: String

    // 継承先で処理を入れたい時にoverrideする
    open fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder = builder

    companion object {
        fun asHttpErrorTypeException(throwable: Throwable) = when (throwable) {
            is HttpException -> {

                val excption = throwable
                var json: String? = null
                try {
                    json = excption.response()?.errorBody()?.string()
                    val map = null // TODO jsonをmapに変換する

                    HttpErrorTypeException(
                        HttpErrorType.StatusCode(
                            excption.code(),
                            map,
                            throwable
                        )
                    )
                } catch (e: IOException) {
                    HttpErrorTypeException(HttpErrorType.Unknown(throwable))
                }
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