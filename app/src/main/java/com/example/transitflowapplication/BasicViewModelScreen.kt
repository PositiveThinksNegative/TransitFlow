package com.example.transitflowapplication

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

open class BasicViewModelScreen : Fragment() {

    protected val mainViewModel: MainViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel.init()
    }

}
