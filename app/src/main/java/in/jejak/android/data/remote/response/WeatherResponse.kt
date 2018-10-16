package `in`.jejak.android.data.remote.response

import `in`.jejak.android.data.room.entity.WeatherEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

data class WeatherResponse(@SerializedName("list") val weatherForecast: List<WeatherEntity>)