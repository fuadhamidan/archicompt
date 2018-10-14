package `in`.jejak.android.utilities

import `in`.jejak.android.R
import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */
object DateUtils {

    /* Milliseconds in a day */
    val DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1)

    /**
     * This method returns the number of milliseconds (UTC time) for today's date at midnight in
     * the local time zone. For example, if you live in California and the day is September 20th,
     * 2016 and it is 6:30 PM, it will return 1474329600000. Now, if you plug this number into an
     * Epoch time converter, you may be confused that it tells you this time stamp represents 8:00
     * PM on September 19th local time, rather than September 20th. We're concerned with the GMT
     * date here though, which is correct, stating September 20th, 2016 at midnight.
     *
     *
     * As another example, if you are in Hong Kong and the day is September 20th, 2016 and it is
     * 6:30 PM, this method will return 1474329600000. Again, if you plug this number into an Epoch
     * time converter, you won't get midnight for your local time zone. Just keep in mind that we
     * are just looking at the GMT date here.
     *
     *
     * This method will ALWAYS return the date at midnight (in GMT time) for the time zone you
     * are currently in. In other words, the GMT date will always represent your date.
     *
     *
     * Since UTC / GMT time are the standard for all time zones in the world, we use it to
     * normalize our dates that are stored in the database. When we extract values from the
     * database, we adjust for the current time zone using time zone offsets.
     *
     * @return The number of milliseconds (UTC / GMT) for today's date at midnight in the local
     * time zone
     */
    /*
         * This number represents the number of milliseconds that have elapsed since January
         * 1st, 1970 at midnight in the GMT time zone.
         *//*
         * This TimeZone represents the device's current time zone. It provides us with a means
         * of acquiring the offset for local time from a UTC time stamp.
         *//*
         * The getOffset method returns the number of milliseconds to add to UTC time to get the
         * elapsed time since the epoch for our current time zone. We pass the current UTC time
         * into this method so it can determine changes to account for daylight savings time.
         *//*
         * UTC time is measured in milliseconds from January 1, 1970 at midnight from the GMT
         * time zone. Depending on your time zone, the time since January 1, 1970 at midnight (GMT)
         * will be greater or smaller. This variable represents the number of milliseconds since
         * January 1, 1970 (GMT) time.
         *//* This method simply converts milliseconds to days, disregarding any fractional days *//*
         * Finally, we convert back to milliseconds. This time stamp represents today's date at
         * midnight in GMT time. We will need to account for local time zone offsets when
         * extracting this information from the database.
         */
    val normalizedUtcMsForToday: Long
        get() {
            val utcNowMillis = System.currentTimeMillis()
            val currentTimeZone = TimeZone.getDefault()
            val gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis).toLong()
            val timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis
            val daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis)

            return TimeUnit.DAYS.toMillis(daysSinceEpochLocal)
        }


    val normalizedUtcDateForToday: Date
        get() {
            val normalizedMilli = normalizedUtcMsForToday
            return Date(normalizedMilli)
        }

    /**
     * This method returns the number of days since the epoch (January 01, 1970, 12:00 Midnight UTC)
     * in UTC time from the current date.
     *
     * @param utcDate A date in milliseconds in UTC time.
     * @return The number of days from the epoch to the date argument.
     */
    private fun elapsedDaysSinceEpoch(utcDate: Long): Long {
        return TimeUnit.MILLISECONDS.toDays(utcDate)
    }

    /**
     * This method will return the local time midnight for the provided normalized UTC date.
     *
     * @param normalizedUtcDate UTC time at midnight for a given date. This number comes from the
     * database
     * @return The local date corresponding to the given normalized UTC date
     */
    private fun getLocalMidnightFromNormalizedUtcDate(normalizedUtcDate: Long): Long {
        /* The timeZone object will provide us the current user's time zone offset */
        val timeZone = TimeZone.getDefault()
        /*
         * This offset, in milliseconds, when added to a UTC date time, will produce the local
         * time.
         */
        val gmtOffset = timeZone.getOffset(normalizedUtcDate).toLong()
        return normalizedUtcDate - gmtOffset
    }

    /**
     * Helper method to convert the database representation of the date into something to display
     * to users. As classy and polished a user experience as "1474061664" is, we can do better.
     *
     *
     * The day string for forecast uses the following logic:
     * For today: "Today, June 8"
     * For tomorrow:  "Tomorrow
     * For the next 5 days: "Wednesday" (just the day name)
     * For all days after that: "Mon, Jun 8" (Mon, 8 Jun in UK, for example)
     *
     * @param context               Context to use for resource localization
     * @param normalizedUtcMidnight The date in milliseconds (UTC midnight)
     * @param showFullDate          Used to show a fuller-version of the date, which always
     * contains either the day of the week, today, or tomorrow, in
     * addition to the date.
     * @return A user-friendly representation of the date such as "Today, June 8", "Tomorrow",
     * or "Friday"
     */


    fun getFriendlyDateString(context: Context, normalizedUtcMidnight: Long, showFullDate: Boolean): String {

        /*
         * NOTE: localDate should be localDateMidnightMillis and should be straight from the
         * database
         *
         * Since we normalized the date when we inserted it into the database, we need to take
         * that normalized date and produce a date (in UTC time) that represents the local time
         * zone at midnight.
         */
        val localDate = getLocalMidnightFromNormalizedUtcDate(normalizedUtcMidnight)

        /*
         * In order to determine which day of the week we are creating a date string for, we need
         * to compare the number of days that have passed since the epoch (January 1, 1970 at
         * 00:00 GMT)
         */
        val daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(localDate)

        /*
         * As a basis for comparison, we use the number of days that have passed from the epoch
         * until today.
         */
        val daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis())

        if (daysFromEpochToProvidedDate == daysFromEpochToToday || showFullDate) {
            /*
             * If the date we're building the String for is today's date, the format
             * is "Today, June 24"
             */
            val dayName = getDayName(context, localDate)
            val readableDate = getReadableDateString(context, localDate)
            if (daysFromEpochToProvidedDate - daysFromEpochToToday < 2) {
                /*
                 * Since there is no localized format that returns "Today" or "Tomorrow" in the API
                 * levels we have to support, we take the name of the day (from SimpleDateFormat)
                 * and use it to replace the date from DateUtils. This isn't guaranteed to work,
                 * but our testing so far has been conclusively positive.
                 *
                 * For information on a simpler API to use (on API > 18), please check out the
                 * documentation on DateFormat#getBestDateTimePattern(Locale, String)
                 * https://developer.android.com/reference/android/text/format/DateFormat.html#getBestDateTimePattern
                 */
                val localizedDayName = SimpleDateFormat("EEEE").format(localDate)
                return readableDate.replace(localizedDayName, dayName)
            } else {
                return readableDate
            }
        } else if (daysFromEpochToProvidedDate < daysFromEpochToToday + 7) {
            /* If the input date is less than a week in the future, just return the day name. */
            return getDayName(context, localDate)
        } else {
            val flags = (DateUtils.FORMAT_SHOW_DATE
                    or DateUtils.FORMAT_NO_YEAR
                    or DateUtils.FORMAT_ABBREV_ALL
                    or DateUtils.FORMAT_SHOW_WEEKDAY)

            return DateUtils.formatDateTime(context, localDate, flags)
        }
    }

    /**
     * Returns a date string in the format specified, which shows an abbreviated date without a
     * year.
     *
     * @param context      Used by DateUtils to format the date in the current locale
     * @param timeInMillis Time in milliseconds since the epoch (local time)
     * @return The formatted date string
     */
    private fun getReadableDateString(context: Context, timeInMillis: Long): String {
        val flags = (DateUtils.FORMAT_SHOW_DATE
                or DateUtils.FORMAT_NO_YEAR
                or DateUtils.FORMAT_SHOW_WEEKDAY)

        return DateUtils.formatDateTime(context, timeInMillis, flags)
    }

    /**
     * Given a day, returns just the name to use for that day.
     * E.g "today", "tomorrow", "Wednesday".
     *
     * @param context      Context to use for resource localization
     * @param dateInMillis The date in milliseconds (UTC time)
     * @return the string day of the week
     */
    private fun getDayName(context: Context, dateInMillis: Long): String {
        /*
         * If the date is today, return the localized version of "Today" instead of the actual
         * day name.
         */
        val daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(dateInMillis)
        val daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis())

        val daysAfterToday = (daysFromEpochToProvidedDate - daysFromEpochToToday).toInt()

        return when (daysAfterToday) {
            0 -> context.getString(R.string.today)
            1 -> context.getString(R.string.tomorrow)

            else -> {
                val dayFormat = SimpleDateFormat("EEEE")
                dayFormat.format(dateInMillis)
            }
        }
    }
}