package com.ko2ic.coroutinesflow.common.repository.http

import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HttpClient(val locator: HttpClientLocator) {

    inline fun <R, reified T> call(crossinline call: suspend (T) -> R): Flow<R> {

        val client = locator.lookup(clazz = T::class.java)

        return flow {
            try {
                emit(call(client))
            } catch (e: Throwable) {
                val exception: HttpErrorTypeException = HttpClientBase.asHttpErrorTypeException(e)
                throw exception
            }
        }.flowOn(Dispatchers.IO)
    }
}
