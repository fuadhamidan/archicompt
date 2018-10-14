package `in`.jejak.android.data.network

import `in`.jejak.android.utilities.InjectorUtils
import android.app.IntentService
import android.content.Intent
import android.util.Log

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class AppSyncIntentService : IntentService("AppSyncIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "Intent service started")
        val networkDataSource = InjectorUtils.provideNetworkDataSource(this.applicationContext)
        networkDataSource.fetchWeather()
    }

    companion object {
        private val LOG_TAG = AppSyncIntentService::class.java.simpleName
    }
}