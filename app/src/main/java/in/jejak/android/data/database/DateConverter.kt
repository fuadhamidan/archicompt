package `in`.jejak.android.data.database

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class DateConverter{
    @TypeConverter
    fun toDate(timestamp: Long?): Date?{
        return if(timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long?{
        return date?.time
    }
}