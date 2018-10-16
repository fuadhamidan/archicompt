package `in`.jejak.android.data.repository

import `in`.jejak.android.data.room.entity.WeatherEntity
import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import java.util.*

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

interface Repository {

    fun addWeather(){}

    fun deleteWeather(date: Date){}

    fun getTotalWeather(): Flowable<Int>

    fun getWeatherbyDate(qdate: Date): LiveData<WeatherEntity>

    fun fetchWeatherbyDate(query: String, format: String, unit: String, days: String): LiveData<List<WeatherEntity>>
}