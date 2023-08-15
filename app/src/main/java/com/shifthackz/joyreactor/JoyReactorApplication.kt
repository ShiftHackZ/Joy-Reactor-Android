package com.shifthackz.joyreactor

import android.app.Application
import com.shifthackz.joyreactor.data.di.dataModule
import com.shifthackz.joyreactor.network.di.networkModule
import com.shifthackz.joyreactor.presentation.di.viewModelModule
import com.shifthackz.joyreactor.storage.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JoyReactorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JoyReactorApplication)
            modules(
                networkModule,
                storageModule,
                *dataModule,
                viewModelModule,
            )
        }
    }
}
