package `in`.jejak.android.data.room

import `in`.jejak.android.data.room.dao.WeatherDao
import `in`.jejak.android.data.room.entity.WeatherEntity
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class RoomDataSource : RoomDatabase(){
    abstract fun weatherDao(): WeatherDao

    companion object {
        fun instance(context: Context): RoomDataSource = Room.databaseBuilder(context.applicationContext, RoomDataSource::class.java, RoomConfig.DATABASE).build()
    }
}