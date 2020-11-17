package com.ko2ic.coroutinesflow.model.annotation

import com.ko2ic.coroutinesflow.model.valueobject.HttpLocateType
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class HttpLocate(val value: HttpLocateType = HttpLocateType.Default)