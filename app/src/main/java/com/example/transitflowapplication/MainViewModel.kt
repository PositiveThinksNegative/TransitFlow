package com.example.transitflowapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val readyLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun init() {
        viewModelScope.launch {
            flow<Unit> {
                delay(1000)
                readyLiveData.value = true
            }.onStart {
                loadingLiveData.value = true
            }.onCompletion {
                loadingLiveData.value = false
            }.launchIn(this)
        }
    }

}
