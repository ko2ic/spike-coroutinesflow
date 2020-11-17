package com.ko2ic.coroutinesflow.model.annotation

import com.ko2ic.coroutinesflow.model.valueobject.HttpClientStrategyType
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class HttpClientStrategy(val value: HttpClientStrategyType = HttpClientStrategyType.Retrofit)