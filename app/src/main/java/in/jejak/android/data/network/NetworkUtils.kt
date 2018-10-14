package `in`.jejak.android.data.network

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

internal object NetworkUtils {

    private val TAG = NetworkUtils::class.java.simpleName

    /*
     * Sunshine was originally built to use OpenWeatherMap's API. However, we wanted to provide
     * a way to much more easily test the app and provide more varied weather data. After all, in
     * Mountain View (Google's HQ), it gets very boring looking at a forecast of perfectly clear
     * skies at 75°F every day... (UGH!) The solution we came up with was to host our own fake
     * weather server. With this server, there are two URL's you can use. The first (and default)
     * URL will return dynamic weather data. Each time the app refreshes, you will get different,
     * completely random weather data. This is incredibly useful for testing the robustness of your
     * application, as different weather JSON will provide edge cases for some of your methods.
     *
     */
    private const val DYNAMIC_WEATHER_URL = "https://andfun-weather.udacity.com/weather"

    private const val STATIC_WEATHER_URL = "https://andfun-weather.udacity.com/staticweather"

    private const val FORECAST_BASE_URL = DYNAMIC_WEATHER_URL

    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API. If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this training.
     */

    /* The format we want our API to return */
    private const val format = "json"
    /* The units we want our API to return */
    private const val units = "metric"


    /* The query parameter allows us to provide a location string to the API */
    private const val QUERY_PARAM = "q"

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private const val FORMAT_PARAM = "mode"
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private const val UNITS_PARAM = "units"
    /* The days parameter allows us to designate how many days of weather data we want */
    private const val DAYS_PARAM = "cnt"

    /**
     * Retrieves the proper URL to query for the weather data.
     *
     * @return URL to query weather service
     */
    val url: URL?
        get() {
            val locationQuery = "Mountain View, CA"
            return buildUrlWithLocationQuery(locationQuery)
        }

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    private fun buildUrlWithLocationQuery(locationQuery: String): URL? {
        val weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
            .appendQueryParameter(QUERY_PARAM, locationQuery)
            .appendQueryParameter(FORMAT_PARAM, format)
            .appendQueryParameter(UNITS_PARAM, units)
            .appendQueryParameter(DAYS_PARAM, Integer.toString(WeatherNetworkDataSource.NUM_DAYS))
            .build()

        return try {
            val weatherQueryUrl = URL(weatherQueryUri.toString())
            Log.v(TAG, "URL: $weatherQueryUrl")
            weatherQueryUrl
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
        }

    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            var response: String? = null
            if (hasInput) {
                response = scanner.next()
            }
            scanner.close()
            return response
        } finally {
            urlConnection.disconnect()
        }
    }
}