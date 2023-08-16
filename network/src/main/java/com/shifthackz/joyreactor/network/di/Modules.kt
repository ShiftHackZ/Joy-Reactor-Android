package com.shifthackz.joyreactor.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shifthackz.joyreactor.network.interceptor.LoggingInterceptor
import com.shifthackz.joyreactor.network.parser.CommentsParser
import com.shifthackz.joyreactor.network.parser.PostsParser
import com.shifthackz.joyreactor.network.parser.SectionsParser
import com.shifthackz.joyreactor.network.qualifier.HttpInterceptors
import com.shifthackz.joyreactor.network.qualifier.NetworkInterceptor
import com.shifthackz.joyreactor.network.qualifier.NetworkInterceptors
import com.shifthackz.joyreactor.network.qualifier.RetrofitCallAdapters
import com.shifthackz.joyreactor.network.qualifier.RetrofitConverterFactories
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val HTTP_TIMEOUT = 1L

val networkModule = module {
    singleOf(::PostsParser)
    singleOf(::CommentsParser)
    singleOf(::SectionsParser)

    single<Gson> { GsonBuilder().setLenient().create() }

    single {
        RetrofitConverterFactories(
            buildList {
                add(GsonConverterFactory.create(get()))
            }
        )
    }

    single {
        RetrofitCallAdapters(
            buildList {
//                add(RxJava3CallAdapterFactory.create())
            }
        )
    }

    single {
        HttpInterceptors(
            listOf(
//                HttpInterceptor(HeaderInterceptor(get(), get())),
            )
        )
    }

    single {
        NetworkInterceptors(
            listOf(
                NetworkInterceptor(LoggingInterceptor().get()),
            )
        )
    }


    single {
        OkHttpClient
            .Builder()
            .apply {
                get<HttpInterceptors>().interceptors.forEach(::addInterceptor)
                get<NetworkInterceptors>().interceptors.forEach(::addNetworkInterceptor)
            }
            .connectTimeout(HTTP_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(HTTP_TIMEOUT, TimeUnit.MINUTES)
            .build()
    }

    single<Retrofit.Builder> {
        Retrofit
            .Builder()
            .apply {
                get<RetrofitConverterFactories>().data.forEach(::addConverterFactory)
                get<RetrofitCallAdapters>().data.forEach(::addCallAdapterFactory)
            }
            .client(get())
    }


}

