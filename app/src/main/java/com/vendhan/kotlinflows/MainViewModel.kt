package com.vendhan.kotlinflows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String>
        get() = _liveData

    private val _stateFlow = MutableStateFlow(value = "Hello World")
    val stateFlow
        get() = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow
        get() = _sharedFlow.asSharedFlow()

    fun triggerLiveData() {
        _liveData.value = "It's LiveData"
    }

    fun triggerFlow(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Emitting $it")
                delay(1000)
            }
        }
    }

    fun triggerStateFlow() {
        _stateFlow.value = "It's StateFlow"
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("It's SharedFlow")
        }
    }
}
