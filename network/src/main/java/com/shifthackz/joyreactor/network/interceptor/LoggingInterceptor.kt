package com.shifthackz.joyreactor.network.interceptor

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

internal class LoggingInterceptor {

    fun get() = HttpLoggingInterceptor { message ->
        Log.d(HTTP_TAG, message)
    }.apply {
//        level = HttpLoggingInterceptor.Level.BODY
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    companion object {
        private const val HTTP_TAG = "HTTP"
    }
}
