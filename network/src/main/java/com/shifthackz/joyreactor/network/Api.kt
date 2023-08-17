package com.shifthackz.joyreactor.network

import android.util.Log
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {

    @GET("/autocomplete/tag")
    suspend fun searchTag(
        @Query("term") query: String,
        @Header("Referer") referer: String = "https://joyreactor.cc/search"
    ): Response<List<String>>
}

class ApiImpl(private val api: Api) {
    suspend fun searchTag(query: String): List<String> {
        val body = api.searchTag(query)?.body()
        Log.d("API", "Body: $body")
        return body ?: listOf()
    }
}