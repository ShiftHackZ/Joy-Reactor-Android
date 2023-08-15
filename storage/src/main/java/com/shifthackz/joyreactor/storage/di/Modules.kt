package com.shifthackz.joyreactor.storage.di

import androidx.room.Room
import com.shifthackz.joyreactor.storage.db.JoyReactorDb
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storageModule = module {

    single {
        Room.databaseBuilder(androidApplication(), JoyReactorDb::class.java, JoyReactorDb.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<JoyReactorDb>().postDao }
    single { get<JoyReactorDb>().authorDao }
    single { get<JoyReactorDb>().tagDao }
    single { get<JoyReactorDb>().contentDao }
}
