package com.ko2ic.coroutinesflow.common.model.exception

import com.ko2ic.coroutinesflow.common.model.enums.HttpErrorType

class HttpErrorTypeException(val errorType: HttpErrorType) : Exception() {
}