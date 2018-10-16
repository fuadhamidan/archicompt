package `in`.jejak.android.features.weather.detail

import `in`.jejak.android.JejakinApp
import `in`.jejak.android.data.repository.WeatherRepository
import `in`.jejak.android.data.room.entity.WeatherEntity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import java.util.*
import javax.inject.Inject


/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */


class DetailActivityViewModel(private val date: Date) : ViewModel(){
    @Inject
    lateinit var repository: WeatherRepository

    init {
        JejakinApp.appComponent.inject(this)
    }

    val weather: LiveData<WeatherEntity> = repository.getWeatherbyDate(date)
}