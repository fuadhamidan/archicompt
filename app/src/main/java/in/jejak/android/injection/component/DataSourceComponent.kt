package `in`.jejak.android.injection.component

import `in`.jejak.android.data.remote.RemoteDataSource
import `in`.jejak.android.injection.module.RemoteModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Component(modules = [RemoteModule::class])
@Singleton
interface DataSourceComponent {
    fun inject(remoteDataSource: RemoteDataSource)
}