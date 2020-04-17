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
    val searchLiveData: MutableLiveData<String> = MutableLiveData()
    val refreshedTimebarLiveData: MutableLiveData<String> = MutableLiveData()

    private val searchFlow = SearchFlow()
    private var searchJob: Job? = null

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

    fun launchTimebarRefresh() {
        viewModelScope {
            refreshTimebar().collect {
                refreshedTimebarLiveData.value = it
            }
        }
    }

    suspend fun launchSearch(s: String) {
        searchJob = viewModelScope {
            searchFlow.searchTerm = s
            searchFlow.collect {
                searchLiveData.value = it
            }
        }

    }

    fun cancelSearch() {
        searchJob?.cancel()
    }

    private fun viewModelScope(scopeToRun: suspend () -> Any): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            scopeToRun()
        }
    }

    private fun launchViewModel(): Flow<Boolean> {
        return flow {
            emit(false)
            delay(3000)
            emit(true)
        }.flowOn(Dispatchers.IO)
    }

    private fun refreshTimebar(): Flow<String> {
        return flow {
            while (true) {
                emit("Refreshed")
                delay(6000)
            }
        }
    }

    @FlowPreview
    inner class SearchFlow : Flow<String> {
        var searchTerm: String = ""

        @InternalCoroutinesApi
        override suspend fun collect(collector: FlowCollector<String>) {
            collector.emit("Search result for: $searchTerm")
        }

    }

}
