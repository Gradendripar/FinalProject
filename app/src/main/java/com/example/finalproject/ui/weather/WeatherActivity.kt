package com.example.finalproject.ui.weather

import com.example.finalproject.ui.weather.weather.Weather
import com.example.finalproject.ui.weather.weather.Forecast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.finalproject.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_weather2.*

class WeatherActivity : AppCompatActivity() {

    val baseURL="http://t.weather.itboy.net/api/weather/city/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_weather2)

        val cityCode = intent.getStringExtra("city_code")
        Log.d("cityCode", "$cityCode")
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(baseURL + cityCode, {
            val gson = Gson()
            val WeatherType = object : TypeToken<Weather>() {}.type
            val weather = gson.fromJson<Weather>(it,WeatherType)
            textView_city.text = weather.cityInfo.city
            textView_province.text = weather.cityInfo.parent
            textView_shidu.text = weather.data.shidu
            textView_wendu.text = weather.data.wendu
            val firstDay = weather.data.forecast.first()
            when (firstDay.type) {
                "晴" -> imageView.setImageResource(R.drawable.sun)
                "阴" -> imageView.setImageResource(R.drawable.cloud)
                "多云" -> imageView.setImageResource(R.drawable.mcloud)
                "正雨" -> imageView.setImageResource(R.drawable.rain)
                else -> imageView.setImageResource(R.drawable.thunder)
            }
            val adapter = ArrayAdapter<Forecast>(
                this,
                android.R.layout.simple_list_item_1,
                weather.data.forecast
            )
            listView.adapter = adapter
            Log.d("WeatherActivity", "${weather.cityInfo.city} ${weather.cityInfo.parent}")
        },{
            Log.d("WeatherActivity", "$it")
        })
        queue.add(stringRequest)
    }
}
