package `in`.jejak.android.features.weather.detail

import `in`.jejak.android.R
import `in`.jejak.android.data.database.WeatherEntry
import `in`.jejak.android.databinding.ActivityWeatherDetailBinding
import `in`.jejak.android.utilities.DateUtils
import `in`.jejak.android.utilities.InjectorUtils
import `in`.jejak.android.utilities.WeatherUtils
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by fuado on 2018/10/13.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class DetailActivity: AppCompatActivity(), LifecycleOwner{
    private var detailBinding: ActivityWeatherDetailBinding? = null
    private lateinit var viewModel: DetailActivityViewModel

    companion object {
        const val WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather_detail)
        val timestamp = intent.getLongExtra(WEATHER_ID_EXTRA, -1)
        val date = DateUtils.normalizedUtcDateForToday

        val factory = InjectorUtils.provideDetailViewModelFactory(this, date)

        viewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel::class.java)
        viewModel.weather.observe(this, Observer{
            if (it != null) bindWeatherToUI(it)
        })
    }

    private fun bindWeatherToUI(weatherEntry: WeatherEntry) {
        /****************
         * Weather Icon *
         */

        val weatherId = weatherEntry.weatherIconId
        val weatherImageId = WeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId)

        /* Set the resource ID on the icon to display the art */
        detailBinding!!.primaryInfo.weatherIcon.setImageResource(weatherImageId)

        /****************
         * Weather Date *
         */
        /*
         * The date that is stored is a GMT representation at midnight of the date when the weather
         * information was loaded for.
         *
         * When displaying this date, one must add the GMT offset (in milliseconds) to acquire
         * the date representation for the local date in local time.
         * SunshineDateUtils#getFriendlyDateString takes care of this for us.
         */
        val localDateMidnightGmt = weatherEntry.date!!.time
        val dateText = DateUtils.getFriendlyDateString(this@DetailActivity, localDateMidnightGmt, true)
        detailBinding!!.primaryInfo.date.text = dateText

        /***********************
         * Weather Description *
         */
        /* Use the weatherId to obtain the proper description */
        val description = WeatherUtils.getStringForWeatherCondition(this@DetailActivity, weatherId)

        /* Create the accessibility (a11y) String from the weather description */
        val descriptionA11y = getString(R.string.a11y_forecast, description)

        /* Set the text and content description (for accessibility purposes) */
        detailBinding!!.primaryInfo.weatherDescription.text = description
        detailBinding!!.primaryInfo.weatherDescription.contentDescription = descriptionA11y

        /* Set the content description on the weather image (for accessibility purposes) */
        detailBinding!!.primaryInfo.weatherIcon.contentDescription = descriptionA11y

        /**************************
         * High (max) temperature *
         */

        val maxInCelsius = weatherEntry.max

        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        val highString = WeatherUtils.formatTemperature(this@DetailActivity, maxInCelsius)

        /* Create the accessibility (a11y) String from the weather description */
        val highA11y = getString(R.string.a11y_high_temp, highString)

        /* Set the text and content description (for accessibility purposes) */
        detailBinding!!.primaryInfo.highTemperature.text = highString
        detailBinding!!.primaryInfo.highTemperature.contentDescription = highA11y

        /*************************
         * Low (min) temperature *
         */

        val minInCelsius = weatherEntry.min
        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        val lowString = WeatherUtils.formatTemperature(this@DetailActivity, minInCelsius)

        val lowA11y = getString(R.string.a11y_low_temp, lowString)

        /* Set the text and content description (for accessibility purposes) */
        detailBinding!!.primaryInfo.lowTemperature.text = lowString
        detailBinding!!.primaryInfo.lowTemperature.contentDescription = lowA11y

        /************
         * Humidity *
         */

        val humidity = weatherEntry.humidity
        val humidityString = getString(R.string.format_humidity, humidity)
        val humidityA11y = getString(R.string.a11y_humidity, humidityString)

        /* Set the text and content description (for accessibility purposes) */
        detailBinding!!.extraDetails.humidity.setText(humidityString)
        detailBinding!!.extraDetails.humidity.contentDescription = humidityA11y

        detailBinding!!.extraDetails.humidityLabel.contentDescription = humidityA11y

        /****************************
         * Wind speed and direction *
         */
        /* Read wind speed (in MPH) and direction (in compass degrees)*/
        val windSpeed = weatherEntry.wind
        val windDirection = weatherEntry.degrees
        val windString = WeatherUtils.getFormattedWind(this@DetailActivity, windSpeed, windDirection)
        val windA11y = getString(R.string.a11y_wind, windString)

        /* Set the text and content description (for accessibility purposes) */
        detailBinding!!.extraDetails.windMeasurement.text = windString
        detailBinding!!.extraDetails.windMeasurement.contentDescription = windA11y
        detailBinding!!.extraDetails.windLabel.contentDescription = windA11y

        /************
         * Pressure *
         */
        val pressure = weatherEntry.pressure

        /*
         * Format the pressure text using string resources. The reason we directly access
         * resources using getString rather than using a method from SunshineWeatherUtils as
         * we have for other data displayed in this Activity is because there is no
         * additional logic that needs to be considered in order to properly display the
         * pressure.
         */
        val pressureString = getString(R.string.format_pressure, pressure)

        val pressureA11y = getString(R.string.a11y_pressure, pressureString)

        /* Set the text and content description (for accessibility purposes) */
        detailBinding!!.extraDetails.pressure.setText(pressureString)
        detailBinding!!.extraDetails.pressure.contentDescription = pressureA11y
        detailBinding!!.extraDetails.pressureLabel.contentDescription = pressureA11y
    }
}