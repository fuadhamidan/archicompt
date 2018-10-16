package `in`.jejak.android.data.remote

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class RemoteConfig{
    companion object {
        const val BASE_API = "https://andfun-weather.udacity.com/"

        //parameter
        const val WEATHER = "weather"

        //query
        const val QUERY_PARAM = "q"
        const val FORMAT_PARAM = "mode"
        const val UNITS_PARAM = "units"
        const val DAYS_PARAM = "cnt"

        //
        const val NUM_DAYS = 14
    }
}