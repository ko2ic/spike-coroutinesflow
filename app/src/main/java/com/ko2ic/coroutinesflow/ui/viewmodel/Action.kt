package com.ko2ic.coroutinesflow.ui.viewmodel

interface Action {
    @Throws(Exception::class)
    fun run()
}