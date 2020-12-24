package com.example.finalproject.ui.watch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.finalproject.R

class WatchFragment : Fragment() {

    companion object {
        fun newInstance() = WatchFragment()
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
        // TODO: Use the ViewModel
    }

}
