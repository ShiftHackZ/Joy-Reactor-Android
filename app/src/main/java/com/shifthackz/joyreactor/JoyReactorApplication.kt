package com.shifthackz.joyreactor

import android.app.Application
import android.util.Log
import com.shifthackz.joyreactor.data.di.dataModule
import com.shifthackz.joyreactor.domain.di.domainModule
import com.shifthackz.joyreactor.network.di.networkModule
import com.shifthackz.joyreactor.nsfw.di.nsfwModule
import com.shifthackz.joyreactor.presentation.di.viewModelModule
import com.shifthackz.joyreactor.storage.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JoyReactorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.currentThread().setUncaughtExceptionHandler { _, t ->
            Log.e("Fatal", "Uncaught", t)
        }
        startKoin {
            androidContext(this@JoyReactorApplication)
            modules(
                nsfwModule,
                networkModule,
                storageModule,
                *dataModule,
                domainModule,
                viewModelModule,
            )
        }
    }
}
