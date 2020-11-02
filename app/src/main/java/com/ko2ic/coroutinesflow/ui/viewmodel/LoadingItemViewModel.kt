package com.ko2ic.coroutinesflow.ui.viewmodel

class LoadingItemViewModel private constructor() : CollectionItemViewModel {

    private object Holder {
        val INSTANCE = LoadingItemViewModel()
    }

    companion object {
        val instance: LoadingItemViewModel by lazy { Holder.INSTANCE }
    }
}