package `in`.jejak.android.injection.module

import `in`.jejak.android.JejakinApp
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by fuado on 2018/10/15.
 * Email    : fuadhamidan@gmail.com
 * ---
 * Description:
 * ...
 */

@Module
class AppModule(private val currencyApplication: JejakinApp) {
    @Provides
    @Singleton
    fun provideContext(): Context = currencyApplication
}