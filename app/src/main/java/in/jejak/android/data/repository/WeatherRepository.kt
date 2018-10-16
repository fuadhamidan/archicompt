package `in`.jejak.android.data.repository

import `in`.jejak.android.data.network.AppSyncIntentService
import `in`.jejak.android.data.remote.RemoteConfig
import `in`.jejak.android.data.remote.RemoteDataSource
import `in`.jejak.android.data.room.RoomDataSource
import `in`.jejak.android.data.room.entity.WeatherEntity
import `in`.jejak.android.utilities.DateUtils
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton



/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Singleton
class WeatherRepository @Inject constructor(private val context: Context,
                                            private val roomDataSource: RoomDataSource,
                                            private val remoteDataSource: RemoteDataSource){

    private val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    private val mDownloadedWeather: MutableLiveData<List<WeatherEntity>> = MutableLiveData()

    val currentWeatherForecasts: LiveData<List<WeatherEntity>>
        get() = mDownloadedWeather

    init {
        Log.d("Kuy","Init repository")

        mDownloadedWeather.observeForever {

            Log.d("Kuy","observerForever")


            Observable.just(it)
                .subscribeOn(Schedulers.io())
                .subscribe { it ->
                    val today = DateUtils.normalizedUtcDateForToday
                    roomDataSource.weatherDao().deleteOldData(today)
                    Log.d("Kuy","Values deleted")

                    for((index, item) in it!!.withIndex()){
                        val dateTimeMillis = DateUtils.normalizedUtcMsForToday + DateUtils.DAY_IN_MILLIS * index
                        it?.get(index)?.date = Date(dateTimeMillis)
                    }

                    roomDataSource.weatherDao().bulkInsert(it!!)
                    Log.d("Kuy","New values inserted")
                }

        }

    }

    private fun isFetchNeeded(): Boolean{
        Log.d("Kuy","isFetchNeeded()")

        var count = 0
        Observable.just(roomDataSource.weatherDao())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                val today = DateUtils.normalizedUtcDateForToday
                count = it.countAllFutureWeather(today)
            }

        return count < RemoteConfig.NUM_DAYS
    }

    fun getWeatherbyDate(date: Date): LiveData<WeatherEntity> {
        Log.d("Kuy","In getWeatherbyDate")

        initializeData()

        return roomDataSource.weatherDao().getWeatherByDate(date)
    }

    @Synchronized
    private fun initializeData() {
        Log.d("Kuy", "hasActiveObservers()" + mDownloadedWeather.hasActiveObservers())
        Log.d("Kuy", "hasObservers()" + mDownloadedWeather.hasObservers())

        if (isFetchNeeded())
            startFetchWeatherService("q","json","metric", RemoteConfig.NUM_DAYS.toString())
    }

    fun requestWeather(query: String, format: String, unit: String, days: String){
        Log.d("Kuy","Fetch weather started")

        val disposable = remoteDataSource.requestWeather(query, format, unit, days)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.weatherForecast.isNotEmpty()) {
                    Log.d("Kuy", "hasActiveObservers()" + mDownloadedWeather.hasActiveObservers())
                    Log.d("Kuy", "hasObservers()" + mDownloadedWeather.hasObservers())

                    Log.d("Kuy", response.weatherForecast[0].pressure.toString())
                    mDownloadedWeather.postValue(response.weatherForecast)
                    Log.d("Kuy", "POST VALUE")
                }
            }, { t: Throwable? -> t?.printStackTrace() })

        allCompositeDisposable.add(disposable)
    }

    private fun startFetchWeatherService(query: String, format: String, unit: String, days: String) {
        val intent = Intent(context, AppSyncIntentService::class.java)

        intent.putExtra("query", query)
        intent.putExtra("format", format)
        intent.putExtra("unit", unit)
        intent.putExtra("days", days)

        Log.d("Kuy","Remote Data Source Service Created")

        context.startService(intent)
    }

    /*val currentWeatherForecasts: LiveData<List<WeatherEntity>>
        get() = mDownloadedWeather*/


}


















/*
class WeatherRepository private constructor(
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

    fun getWeatherbyDate(date: Date): LiveData<WeatherEntity>{
        initializeData()
        return mWeatherDao.getWeatherByDate(date)
    }

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

    private fun deleteOldData() {
        // TODO Finish this method when instructed
    }

    private// TODO Finish this method when instructed
    val isFetchNeeded: Boolean
        get() {
            val today = DateUtils.normalizedUtcDateForToday
            val count = mWeatherDao.countAllFutureWeather(today)
            return count < WeatherNetworkDataSource.NUM_DAYS
        }

    private fun startFetchWeatherService() {
        mWeatherNetworkDataSource.startFetchWeatherService()
    }
}*/
