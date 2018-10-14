package `in`.jejak.android.data.network

import `in`.jejak.android.AppExecutors
import `in`.jejak.android.data.database.WeatherEntry
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.*
import java.util.concurrent.TimeUnit







/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class WeatherNetworkDataSource private constructor(private val mContext: Context, private val mExecutors: AppExecutors) {

    // LiveData storing the latest downloaded weather forecasts
    val mDownloadedWeatherForecasts: MutableLiveData<Array<WeatherEntry>> = MutableLiveData()

    /**
     * Starts an intent service to fetch the weather.
     */
    fun startFetchWeatherService() {
        val intentToFetch = Intent(mContext, AppSyncIntentService::class.java)
        mContext.startService(intentToFetch)
        Log.d(LOG_TAG, "Service created")
    }

    /**
     * Schedules a repeating job service which fetches the weather.
     */
    fun scheduleRecurringFetchWeatherSync() {
        val driver = GooglePlayDriver(mContext)
        val dispatcher = FirebaseJobDispatcher(driver)

        // Create the Job to periodically sync Sunshine
        val syncSunshineJob = dispatcher.newJobBuilder()
            /* The Service that will be used to sync Sunshine's data */
            .setService(AppFirebaseJobService::class.java)
            /* Set the UNIQUE tag used to identify this Job */
            .setTag(SUNSHINE_SYNC_TAG)
            /*
                 * Network constraints on which this Job should run. We choose to run on any
                 * network, but you can also choose to run only on un-metered networks or when the
                 * device is charging. It might be a good idea to include a preference for this,
                 * as some users may not want to download any data on their mobile plan. ($$$)
                 */
            .setConstraints(Constraint.ON_ANY_NETWORK)
            /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
            .setLifetime(Lifetime.FOREVER)
            /*
                 * We want Sunshine's weather data to stay up to date, so we tell this Job to recur.
                 */
            .setRecurring(true)
            /*
                 * We want the weather data to be synced every 3 to 4 hours. The first argument for
                 * Trigger's static executionWindow method is the start of the time frame when the
                 * sync should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
            .setTrigger(
                Trigger.executionWindow(
                    SYNC_INTERVAL_SECONDS,
                    SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                )
            )
            /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
            .setReplaceCurrent(true)
            /* Once the Job is ready, call the builder's build method to return the Job */
            .build()

        // Schedule the Job with the dispatcher
        dispatcher.schedule(syncSunshineJob)
        Log.d(LOG_TAG, "Job scheduled")
    }

    /**
     * Gets the newest weather
     */
    internal fun fetchWeather() {
        Log.d(LOG_TAG, "Fetch weather started")
        mExecutors.networkIO().execute {
            try {

                // The getUrl method will return the URL that we need to get the forecast JSON for the
                // weather. It will decide whether to create a URL based off of the latitude and
                // longitude or off of a simple location as a String.

                val weatherRequestUrl = NetworkUtils.url

                // Use the URL to retrieve the JSON
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl!!)

                // Parse the JSON into a list of weather forecasts
                val response = OpenWeatherJsonParser().parse(jsonWeatherResponse!!)
                Log.d(LOG_TAG, "JSON Parsing finished")


                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the
                // SunshineRepository.
                if (response != null && response.weatherForecast.size !== 0) {
                    Log.d(
                        LOG_TAG, "JSON not null and has " + response.weatherForecast.size
                                + " values"
                    )
                    Log.d(
                        LOG_TAG, String.format(
                            "First value is %1.0f and %1.0f",
                            response.weatherForecast[0].min,
                            response.weatherForecast[0].max
                        )
                    )

                    // TODO Finish this method when instructed.
                    // Will eventually do something with the downloaded data
                    mDownloadedWeatherForecasts.postValue(response.weatherForecast)
                }
            } catch (e: Exception) {
                // Server probably invalid
                e.printStackTrace()
            }
        }
    }

    companion object {
        // The number of days we want our API to return, set to 14 days or two weeks
        const val NUM_DAYS = 14
        private val LOG_TAG = WeatherNetworkDataSource::class.java.simpleName

        // Interval at which to sync with the weather. Use TimeUnit for convenience, rather than
        // writing out a bunch of multiplication ourselves and risk making a silly mistake.
        private const val SYNC_INTERVAL_HOURS = 3
        private const val SUNSHINE_SYNC_TAG = "sunshine-sync"

        private val SYNC_INTERVAL_SECONDS = TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS.toLong()).toInt()
        private val SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3

        /**
         * Get the singleton for this class
         */
        fun getInstance(context: Context, executors: AppExecutors): WeatherNetworkDataSource = WeatherNetworkDataSource(context.applicationContext, executors)
    }

}