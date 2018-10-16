package `in`.jejak.android.data.remote

import `in`.jejak.android.data.remote.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

interface RemoteService {
    @GET(RemoteConfig.WEATHER)
    fun getWeather(
        @Query(RemoteConfig.QUERY_PARAM) query: String,
        @Query(RemoteConfig.FORMAT_PARAM) format: String,
        @Query(RemoteConfig.UNITS_PARAM) unit: String,
        @Query(RemoteConfig.DAYS_PARAM) days: String
    ): Observable<WeatherResponse>
}
