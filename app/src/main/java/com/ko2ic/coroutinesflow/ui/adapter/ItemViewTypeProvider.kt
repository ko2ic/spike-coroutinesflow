package com.ko2ic.coroutinesflow.ui.adapter

import androidx.databinding.library.baseAdapters.BR
import com.ko2ic.coroutinesflow.ui.viewmodel.CollectionItemViewModel

interface ItemViewTypeProvider {

    fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int

    fun getBindingVariableId(modelCollectionItem: CollectionItemViewModel) = BR.viewModel
}