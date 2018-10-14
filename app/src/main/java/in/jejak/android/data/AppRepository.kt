package `in`.jejak.android.data

import `in`.jejak.android.AppExecutors
import `in`.jejak.android.data.database.WeatherDao
import `in`.jejak.android.data.database.WeatherEntry
import `in`.jejak.android.data.network.WeatherNetworkDataSource
import `in`.jejak.android.utilities.DateUtils
import android.arch.lifecycle.LiveData
import android.util.Log
import java.util.*


/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class AppRepository private constructor(
    private val mWeatherDao: WeatherDao,
    private val mWeatherNetworkDataSource: WeatherNetworkDataSource,
    private val mExecutors: AppExecutors) {

    private var mInitialized = false

    init {
        var networkData = mWeatherNetworkDataSource.mDownloadedWeatherForecasts
        networkData.observeForever { newForecastsFromNetwork ->
            mExecutors.diskIO().execute {
                val today = DateUtils.normalizedUtcDateForToday
                mWeatherDao.deleteOldData(today)
                Log.d(LOG_TAG, "Values deleted")
                // Insert our new weather data into Sunshine's database
                mWeatherDao.bulkInsert(*newForecastsFromNetwork!!)
                Log.d(LOG_TAG, "New values inserted")
            }
        }
    }

    companion object {
        private val LOG_TAG = AppRepository::class.java.simpleName

        fun instance(weatherDao: WeatherDao,
                     weatherNetworkDataSource: WeatherNetworkDataSource,
                     executors: AppExecutors): AppRepository =
            AppRepository(weatherDao, weatherNetworkDataSource, executors)
    }

    fun getWeatherbyDate(date: Date): LiveData<WeatherEntry>{
        initializeData()
        return mWeatherDao.getWeatherByDate(date)
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    @Synchronized
    private fun initializeData() {
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return
        mInitialized = true

        mExecutors.diskIO().execute {
            if (isFetchNeeded)
                startFetchWeatherService()
        }
    }

    /**
     * Database related operations
     */

    /**
     * Deletes old weather data because we don't need to keep multiple days' data
     */
    private fun deleteOldData() {
        // TODO Finish this method when instructed
    }

    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private// TODO Finish this method when instructed
    val isFetchNeeded: Boolean
        get() {
            val today = DateUtils.normalizedUtcDateForToday
            val count = mWeatherDao.countAllFutureWeather(today)
            return count < WeatherNetworkDataSource.NUM_DAYS
        }

    /**
     * Network related operation
     */

    private fun startFetchWeatherService() {
        mWeatherNetworkDataSource.startFetchWeatherService()
    }
}