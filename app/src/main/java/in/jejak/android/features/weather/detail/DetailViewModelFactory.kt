package `in`.jejak.android.features.weather.detail

import `in`.jejak.android.data.AppRepository
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

class DetailViewModelFactory(private val mRepository: AppRepository, private val date: Date) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailActivityViewModel(mRepository, date) as T
    }
}