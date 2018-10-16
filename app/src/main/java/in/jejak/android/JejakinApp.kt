package `in`.jejak.android

import `in`.jejak.android.injection.component.AppComponent
import `in`.jejak.android.injection.component.DaggerAppComponent
import `in`.jejak.android.injection.module.AppModule
import `in`.jejak.android.injection.module.RemoteModule
import `in`.jejak.android.injection.module.RoomModule
import android.app.Application

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

class JejakinApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule())
            .remoteModule(RemoteModule()).build()
    }
}
