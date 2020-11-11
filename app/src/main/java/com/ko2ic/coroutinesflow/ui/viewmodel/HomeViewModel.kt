package com.ko2ic.coroutinesflow.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import com.ko2ic.coroutinesflow.common.repository.http.HttpClient
import com.ko2ic.coroutinesflow.common.ui.viewmodel.Action
import com.ko2ic.coroutinesflow.common.ui.viewmodel.toFlow
import com.ko2ic.coroutinesflow.repository.CommentRepository
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientDefault
import com.ko2ic.coroutinesflow.repository.http.common.HttpClientErrorMock
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class HomeViewModel : ViewModel() {

    val input = ObservableField("")

    // LiveData不要説(observeしないし)
    val viewModels = ObservableArrayList<CommentViewModel>()

    val freeInput = ObservableField("")
    val freeOutput = ObservableField("")

    init {
        freeInput.toFlow().onEach {
            freeOutput.set(it)
        }.launchIn(viewModelScope)
    }

    fun create() {
        load(1)
    }

    fun onSearchClick(): Action = Action {
        load(Integer.parseInt(input.get()!!))
    }

    fun onSearchClick2(): Action = Action {
        // HttpClientErrorMockで必ずエラーが出るAPIにしている
        CommentRepository(HttpClient(HttpClientErrorMock())).error().onEach {
        }.catch { cause ->
            val e = cause as HttpErrorTypeException
            print(e.errorType)
        }.onCompletion {
            // TODO
            print("onCompletion")
        }.launchIn(viewModelScope)
    }

    private fun load(postId: Int) {
        CommentRepository(HttpClient(HttpClientDefault())).fetchComments(postId).onEach {
            it?.map { entity -> CommentViewModel(entity) }.orEmpty().also { viewModels ->
                this@HomeViewModel.render(viewModels)
            }
        }.catch { cause ->
            val e = cause as HttpErrorTypeException
        }.onCompletion {
            // TODO
        }.launchIn(viewModelScope)
    }

    private fun render(itemViewModels: List<CommentViewModel>) {
        viewModels.clear()
        viewModels.addAll(itemViewModels)
    }

//    private fun load(postId: Int) {
//        viewModelScope.launch(Dispatchers.Main) {
//            try {
//                CommentRepository(HttpClient(HttpClientDefault())).fetchComments(postId).collect {
//                    it?.map { entity -> CommentViewModel(entity) }.orEmpty().also { viewModels ->
//                        this@HomeViewModel.render(viewModels)
//                    }
//                }
//
//            } catch (e: HttpErrorTypeException) {
//                // TODO
//            }
//        }
//    }

//    private fun load(postId: Int) {
//        viewModelScope.launch(Dispatchers.Main) {
//            runCatching {
//                CommentRepository(HttpClient(HttpClientDefault())).fetchComments(postId)
//            }.onSuccess {
//                it.collect {
//                    it?.map { entity -> CommentViewModel(entity) }.orEmpty().also { viewModels ->
//                        this@HomeViewModel.render(viewModels)
//                    }
//                }
//            }.onFailure {
//                val e = it as HttpErrorTypeException
//                // TODO
//            }
//        }
//    }
}