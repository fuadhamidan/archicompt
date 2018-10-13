package `in`.jejak.android.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context


/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Database(entities = [WeatherEntry::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun weatherDao(): WeatherDao

    companion object {
        fun instance(context: Context): AppDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "jejakin").build()
    }
}