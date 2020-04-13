package com.example.transitflowapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*

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

        mainViewModel.readyLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "READY: " + it.toString(), Toast.LENGTH_SHORT).show()
        })

        mainViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { display ->
            //Log.d("test", "LOADING: " + it.toString())
            //Toast.makeText(context,"LOADING: " + it.toString(), Toast.LENGTH_SHORT).show()
            progressBar.visibility = if (display) VISIBLE else GONE
        })
    }

}
