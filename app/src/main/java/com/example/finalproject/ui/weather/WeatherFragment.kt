package com.example.finalproject.ui.weather

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.finalproject.R
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        viewModel.cities.observe(viewLifecycleOwner, Observer {
            val cities = it
            val adapter = activity?.let{it -> ArrayAdapter<City>(it , android.R.layout.simple_list_item_1, cities)}
            listView.adapter = adapter
            listView.setOnItemClickListener { _ , _ , position , _ ->
                val cityCode = cities[position].city_code
                val intent = Intent(activity , WeatherActivity::class.java)
                intent.putExtra("city_code", cityCode)
                startActivity(intent)
            }
        })
    }

}
