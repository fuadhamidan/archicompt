package `in`.jejak.android.utilities

import `in`.jejak.android.AppExecutors
import `in`.jejak.android.data.AppRepository
import `in`.jejak.android.data.database.AppDatabase
import `in`.jejak.android.data.network.WeatherNetworkDataSource
import `in`.jejak.android.features.weather.detail.DetailViewModelFactory
import android.content.Context
import java.util.*

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

object InjectorUtils {
    fun provideRepository(context: Context): AppRepository {
        val database = AppDatabase.instance(context.applicationContext)
        val executors = AppExecutors.instance()
        val networkDataSource = WeatherNetworkDataSource.getInstance(context.applicationContext, executors)
        return AppRepository.instance(database.weatherDao(), networkDataSource, executors)
    }

    fun provideNetworkDataSource(context: Context): WeatherNetworkDataSource {
        val executors = AppExecutors.instance()
        return WeatherNetworkDataSource.getInstance(context.applicationContext, executors)
    }

    fun provideDetailViewModelFactory(context: Context, date: Date): DetailViewModelFactory {
        val repository = provideRepository(context.applicationContext)
        return DetailViewModelFactory(repository, date)
    }
}