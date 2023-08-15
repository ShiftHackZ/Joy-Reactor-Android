package com.shifthackz.joyreactor.network.di

import com.shifthackz.joyreactor.network.parser.PostsParser
import org.koin.dsl.module

val networkModule = module {
    single { PostsParser() }
}
