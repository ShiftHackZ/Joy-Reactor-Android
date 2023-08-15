package com.shifthackz.joyreactor.network.extensions

import retrofit2.Retrofit

internal fun Retrofit.Builder.withBaseUrl(baseUrl: String): Retrofit = this
    .baseUrl(baseUrl)
    .build()
