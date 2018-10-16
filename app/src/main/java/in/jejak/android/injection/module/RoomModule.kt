package `in`.jejak.android.injection.module

import `in`.jejak.android.data.room.RoomDataSource
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
class RoomModule {
    @Provides
    @Singleton
    fun provideRoomDataSource(context: Context) = RoomDataSource.instance(context)
}
