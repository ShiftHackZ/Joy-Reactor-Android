package com.shifthackz.joyreactor.presentation.navigation

import android.util.Log

enum class Route(val value: String) {
    POSTS("posts/{url}"),
    TAG("tag");

    companion object {
        fun build(route: Route, params: Map<String, String> = mapOf()): String {
            var value = route.value
            for (key in params.keys) {
                value = value.replace("{$key}", params[key].toString())
            }
            Log.d("RouteBuilder", value)
            return value
        }
    }
}
