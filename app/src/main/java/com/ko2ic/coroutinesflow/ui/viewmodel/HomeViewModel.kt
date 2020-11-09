package com.ko2ic.coroutinesflow.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ko2ic.coroutinesflow.common.repository.http.HttpClient
import com.ko2ic.coroutinesflow.repository.CommentRepository
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // TODO ここをLiveDataにできるかどうか
    val viewModels = ObservableArrayList<CommentViewModel>()

    private val _list = MutableLiveData<List<CommentViewModel>>()

    val list: LiveData<List<CommentViewModel>> = _list

    fun create() {
        viewModelScope.launch(Dispatchers.Main) {
            CommentRepository(HttpClient(HttpClientDefault())).fetchComments(1).collect {
                it?.map { entity -> CommentViewModel(entity) }.orEmpty().also { viewModels ->
                    this@HomeViewModel.render(viewModels)
                }
            }
        }
    }

    fun render(itemViewModels: List<CommentViewModel>) {
        viewModels.clear()
        viewModels.addAll(itemViewModels)
    }
}