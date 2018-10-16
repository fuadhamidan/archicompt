package `in`.jejak.android.data.room.dao

import `in`.jejak.android.data.room.entity.WeatherEntity
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import java.util.*


/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Dao
interface WeatherDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(weather: List<WeatherEntity>)

    @Query("SELECT * FROM weather WHERE date == :date")
    fun getWeatherByDate(date: Date): LiveData<WeatherEntity>

    @Query("SELECT COUNT(id) FROM weather WHERE date >= :date")
    fun countAllFutureWeather(date: Date): Int

    @Query("DELETE FROM weather WHERE date < :date")
    fun deleteOldData(date: Date)
}