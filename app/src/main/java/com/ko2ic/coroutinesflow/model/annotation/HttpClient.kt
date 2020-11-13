package com.ko2ic.coroutinesflow.model.annotation

import com.ko2ic.coroutinesflow.model.valueobject.HttpClientType
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class HttpClient(val value: HttpClientType = HttpClientType.Default)