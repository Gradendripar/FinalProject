package com.example.finalproject.ui.watch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.finalproject.R
import kotlinx.android.synthetic.main.fragment_watch.*

class WatchFragment : Fragment() {

    companion object {
        fun newInstance() = WatchFragment()
        var second=0
    }

    private lateinit var viewModel: WatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WatchViewModel::class.java)
        viewModel.seconds.observe(this, Observer {
            second=it
            val hours = it / 3600
            val minutes = (it % 3600) / 60
            val secs = it % 60
            textView_time.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
        })

        button_start.setOnClickListener {
            viewModel.start()
        }

        button_stop.setOnClickListener {
            viewModel.stop()
        }

        button_restart.setOnClickListener {
            viewModel.restart()
        }
    }
    override fun onStop() {
        super.onStop()
        viewModel.saveData()
    }

}
