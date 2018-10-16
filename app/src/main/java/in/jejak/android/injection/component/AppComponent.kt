package `in`.jejak.android.injection.component

import `in`.jejak.android.data.network.AppSyncIntentService
import `in`.jejak.android.data.remote.RemoteDataSource
import `in`.jejak.android.features.weather.detail.DetailActivityViewModel
import `in`.jejak.android.injection.module.AppModule
import `in`.jejak.android.injection.module.RemoteModule
import `in`.jejak.android.injection.module.RoomModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Component(modules = [AppModule::class, RoomModule::class, RemoteModule::class])
@Singleton
interface AppComponent {
    fun inject(detailViewModel: DetailActivityViewModel)

    //data source
    fun inject(remoteDataSource: RemoteDataSource)


    fun inject(appSyncIntentService: AppSyncIntentService)
}