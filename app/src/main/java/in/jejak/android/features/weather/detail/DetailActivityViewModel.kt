package `in`.jejak.android.features.weather.detail

import `in`.jejak.android.data.AppRepository
import `in`.jejak.android.data.database.WeatherEntry
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import java.util.*


/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */


class DetailActivityViewModel(appRepository: AppRepository, date: Date) : ViewModel() {
    // Weather forecast the user is looking at
    val weather: LiveData<WeatherEntry> = appRepository.getWeatherbyDate(date)
}