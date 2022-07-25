package com.vendhan.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 2
    )
    val sharedFlow
        get() = _sharedFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            repeat(60) {
                _sharedFlow.emit(it)
                delay(1000L)
            }
        }
    }

    fun triggerFlow(): Flow<Int> {
        return flow {
            repeat(60) {
                emit(it)
                delay(1000L)
            }
        }
    }
}
