package com.example.transitflowapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

class MainFragment : BasicViewModelScreen() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.support_simple_spinner_dropdown_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.readyLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("test", "READY: " + it.toString())
        })

        mainViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("test", "LOADING: " + it.toString())
        })
    }

}
