package `in`.jejak.android.features.weather.detail

import `in`.jejak.android.data.database.WeatherEntry
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel



/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */


class DetailActivityViewModel : ViewModel() {
    // Weather forecast the user is looking at
    var weather: MutableLiveData<WeatherEntry> = MutableLiveData()
}