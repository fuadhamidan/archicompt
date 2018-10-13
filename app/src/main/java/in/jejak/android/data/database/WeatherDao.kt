package `in`.jejak.android.data.database

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
    fun bulkInsert(vararg weather: WeatherEntry)

    @Query("SELECT * FROM weather WHERE date = :date")
    fun getWeatherByDate(date: Date): WeatherEntry
}