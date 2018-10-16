package `in`.jejak.android.data.network

import `in`.jejak.android.JejakinApp
import `in`.jejak.android.data.repository.WeatherRepository
import android.app.IntentService
import android.content.Intent
import android.util.Log
import javax.inject.Inject

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class AppSyncIntentService: IntentService("AppSyncIntentService") {

    @Inject
    lateinit var remoteDatSource: WeatherRepository

    override fun onCreate() {
        super.onCreate()

        JejakinApp.appComponent.inject(this)
    }

    override fun onHandleIntent(intent: Intent) {
        Log.d(LOG_TAG, "Intent service started")

        val query = intent.getStringExtra("query")
        val format = intent.getStringExtra("format")
        val unit = intent.getStringExtra("unit")
        val days = intent.getStringExtra("days")

        this.remoteDatSource.requestWeather(query, format, unit, days)
    }

    companion object {
        private val LOG_TAG = AppSyncIntentService::class.java.simpleName
    }
}