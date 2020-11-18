package com.ko2ic.coroutinesflow.common.ui.viewmodel

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T> ObservableField<T>.toFlow(): Flow<T> {
    return callbackFlow {

        val callback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                observable: Observable,
                i: Int
            ) {
                try {
                    offer(this@toFlow.get()!!)
                } catch (e: Exception) {
                    cancel()
                }
            }
        }
        this@toFlow.addOnPropertyChangedCallback(callback)
        awaitClose {
            this@toFlow.removeOnPropertyChangedCallback(callback)
            cancel()
        }
    }
}



