package com.ko2ic.coroutinesflow.common.repository.http

import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HttpClientCaller {

    fun <R> call(call: suspend () -> R): Flow<R> {

        return flow {
            try {
                emit(call())
            } catch (e: Throwable) {
                val exception: HttpErrorTypeException = HttpClientBase.asHttpErrorTypeException(e)
                throw exception
            }
        }.flowOn(Dispatchers.IO)
    }
}
