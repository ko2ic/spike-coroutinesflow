package com.ko2ic.coroutinesflow.ui.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ko2ic.coroutinesflow.common.model.exception.HttpErrorTypeException
import com.ko2ic.coroutinesflow.common.ui.viewmodel.Action
import com.ko2ic.coroutinesflow.common.ui.viewmodel.toFlow
import com.ko2ic.coroutinesflow.model.Comment
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class HomeViewModel @ViewModelInject constructor(
    private val comment: Comment,
) : ViewModel() {

    val input = ObservableField("")

    // LiveData不要説(observeしないし)
    val viewModels = ObservableArrayList<CommentViewModel>()

    val freeInput = ObservableField("")
    val freeOutput = ObservableField("この文言は見えないはず")
    val freeOutputVisivility = ObservableField(false)

    init {
        freeInput.toFlow().onEach {
            freeOutputVisivility.set(true)
            freeOutput.set(it)
        }.launchIn(viewModelScope)
    }

    fun create() {
        load(1)
    }

    fun onSearchClick(): Action = Action {
        if (!input.get().isNullOrBlank()) {
            load(Integer.parseInt(input.get()!!))
        }
        freeOutputVisivility.set(false)
    }

    fun onSearchClick2(): Action = Action {
        // HttpClientErrorMockで必ずエラーが出るAPIにしている
        comment.error().onEach {
        }.catch { cause ->
            val e = cause as HttpErrorTypeException
            print(e.errorType)
            freeOutput.set(e.errorType.toString())
            freeOutputVisivility.set(true)
        }.onCompletion {
            // TODO
            print("onCompletion")
        }.launchIn(viewModelScope)
    }

    fun onSearchClickKtor(): Action = Action {
        if (!input.get().isNullOrBlank()) {
            comment.fetchCommentsByKtor(Integer.parseInt(input.get()!!)).onEach {
                it?.map { entity -> CommentViewModel(entity) }.orEmpty().also { viewModels ->
                    this@HomeViewModel.render(viewModels)
                }
            }.catch { cause ->
                val e = cause as HttpErrorTypeException
                freeOutput.set(e.errorType.toString())
                freeOutputVisivility.set(true)
            }.onCompletion {
                // TODO
            }.launchIn(viewModelScope)
        }
        freeOutputVisivility.set(false)
    }

    fun onSearchClick2Ktor(): Action = Action {
        // HttpClientErrorMockで必ずエラーが出るAPIにしている
        comment.errorByKtor().onEach {
        }.catch { cause ->
            val e = cause as HttpErrorTypeException
            print(e.errorType)
            freeOutput.set(e.errorType.toString())
            freeOutputVisivility.set(true)
        }.onCompletion {
            // TODO
            print("onCompletion")
        }.launchIn(viewModelScope)
    }

    private fun load(postId: Int) {
        comment.fetchComments(postId).onEach {
            it?.map { entity -> CommentViewModel(entity) }.orEmpty().also { viewModels ->
                this@HomeViewModel.render(viewModels)
            }
        }.catch { cause ->
            val e = cause as HttpErrorTypeException
            freeOutput.set(e.errorType.toString())
            freeOutputVisivility.set(true)
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