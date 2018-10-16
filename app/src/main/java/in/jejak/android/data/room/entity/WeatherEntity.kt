package `in`.jejak.android.data.room.entity

import `in`.jejak.android.data.room.RoomConfig
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Entity(tableName = RoomConfig.TABLE_WEATHER, indices = [Index(value = ["date"], unique = true)])
class WeatherEntity{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
        private set
    var weatherIconId: Int = 0
        private set
    var date: Date? = null

    var min: Double = 0.toDouble()
        private set
    var max: Double = 0.toDouble()
        private set
    var humidity: Double = 0.toDouble()
        private set
    var pressure: Double = 0.toDouble()
        private set
    var wind: Double = 0.toDouble()
        private set
    var degrees: Double = 0.toDouble()
        private set

    @Ignore
    constructor(weatherIconId: Int, date: Date, min: Double, max: Double, humidity: Double, pressure: Double, wind: Double, degrees: Double) {
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
    }

    /** Constructor used by Room to create WeatherEntries */
    constructor(id: Int, weatherIconId: Int, date: Date, min: Double, max: Double, humidity: Double, pressure: Double, wind: Double, degrees: Double) {
        this.id = id
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
    }

}