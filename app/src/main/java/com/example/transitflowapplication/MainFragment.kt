package com.example.transitflowapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainFragment : BasicViewModelScreen() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.readyLiveData.observe(viewLifecycleOwner, Observer { isReady ->
            if (isReady) {
                mainViewModel.launchTimebarRefresh()

                CoroutineScope(Dispatchers.Main).launch {
                    flow {
                        emit("test 1")
                        delay(20)
                        emit("test 2")
                        delay(310)
                        emit("test 3")
                    }.debounce(300)
                        .collect {
                            mainViewModel.launchSearch(it)
                        }
                }
            }
        })

        mainViewModel.refreshedTimebarLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        mainViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { display ->
            progressBar.visibility = if (display) VISIBLE else GONE
        })

        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}
