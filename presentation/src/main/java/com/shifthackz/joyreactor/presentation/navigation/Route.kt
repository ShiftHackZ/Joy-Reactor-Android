package com.shifthackz.joyreactor.presentation.navigation

import android.util.Log

enum class Route(val value: String) {
    POSTS("posts/{url}"),
    HOME("home"),
    HOME_FEED("home_feed"),
    HOME_FEED_NEW("home_feed_new"),
    HOME_FEED_GOOD("home_feed_good"),
    HOME_FEED_BEST("home_feed_best");

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
