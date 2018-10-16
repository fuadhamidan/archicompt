package `in`.jejak.android.features.weather.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import java.util.*


/**
 * Created by fuado on 2018/10/14.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class DetailViewModelFactory(private val date: Date) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailActivityViewModel(date) as T
    }
}