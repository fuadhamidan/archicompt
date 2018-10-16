package `in`.jejak.android.data.remote

import javax.inject.Inject

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class RemoteDataSource @Inject constructor(private var remoteService: RemoteService){

    fun requestWeather(query: String, format: String, unit: String, days: String) = remoteService.getWeather(query, format, unit, days)


    /*private val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    private val mDownloadedWeather: MutableLiveData<List<WeatherEntity>> = MutableLiveData()

    val currentWeatherForecasts: LiveData<List<WeatherEntity>>
        get() = mDownloadedWeather

    fun requestWeather(query: String, format: String, unit: String, days: String){
        Log.d("Kuy","Fetch weather started")

        val disposable = remoteService.getWeather(query, format, unit, days)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.weatherForecast.isNotEmpty()) {


                    Log.d("Kuy", "hasActiveObservers()" + currentWeatherForecasts.hasActiveObservers())
                    Log.d("Kuy", "hasObservers()" + currentWeatherForecasts.hasObservers())

                    Log.d("Kuy", response.weatherForecast[0].pressure.toString())
                    mDownloadedWeather.postValue(response.weatherForecast)
                    Log.d("Kuy", "POST VALUE")
                }
            }, { t: Throwable? -> t?.printStackTrace() })

        allCompositeDisposable.add(disposable)
    }

    fun startFetchWeatherService(query: String, format: String, unit: String, days: String) {
        val intent = Intent(context, AppSyncIntentService::class.java)

        intent.putExtra("query", query)
        intent.putExtra("format", format)
        intent.putExtra("unit", unit)
        intent.putExtra("days", days)

        Log.d("Kuy","Remote Data Source Service Created")

        context.startService(intent)
    }*/
}