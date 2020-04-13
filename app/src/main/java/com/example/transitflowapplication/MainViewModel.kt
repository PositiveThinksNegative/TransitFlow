package com.example.transitflowapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val readyLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun init() {
        viewModelScope {
            launchViewModel()
                .onStart {
                loadingLiveData.value = true
            }.onCompletion {
                loadingLiveData.value = false
            }.collect {
                readyLiveData.value = it
            }
        }
    }

    private fun viewModelScope(scopeToRun: suspend () -> Any) : Job {
        return viewModelScope.launch(Dispatchers.Main) {
            scopeToRun()
        }
    }

    private fun launchViewModel(): Flow<Boolean> {
        return flow {
            emit(false)
            delay(5000)
            emit(true)
        }.flowOn(Dispatchers.IO)
    }
}
