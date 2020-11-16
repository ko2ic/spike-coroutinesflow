package com.ko2ic.coroutinesflow.common.repository.http

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import com.ko2ic.coroutinesflow.common.model.valueobject.enums.HttpErrorType
import io.ktor.client.features.*
import io.ktor.client.statement.*
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
        suspend fun asHttpErrorTypeException(throwable: Throwable) = when (throwable) {
            is HttpException -> {
                // Retrofit用
                val excption = throwable
                var json: String? = null
                try {
                    json = excption.response()?.errorBody()?.string()

                    val element = if (json == null) null else Json.parseToJsonElement(json)
                    HttpErrorTypeException(
                        HttpErrorType.StatusCode(
                            excption.code(),
                            element,
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
                val element = if (json == null) null else Json.parseToJsonElement(json)
                HttpErrorTypeException(
                    HttpErrorType.StatusCode(
                        response.status.value,
                        element,
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