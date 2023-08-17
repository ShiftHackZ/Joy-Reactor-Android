package com.shifthackz.joyreactor.presentation.navigation

enum class Route(val value: String) {
    POSTS("posts/{${Argument.URL}}"),
    HOME("home"),
    HOME_FEED("home_feed"),
    HOME_FEED_NEW("home_feed_new"),
    HOME_FEED_GOOD("home_feed_good"),
    HOME_FEED_BEST("home_feed_best"),
    HOME_FEED_TEST("home_feed_test"),
    HOME_DISCUSSED("home_discussed"),
    HOME_DISCUSSED_ALL("home_discussed_all"),
    HOME_DISCUSSED_GOOD("home_discussed_good"),
    HOME_DISCUSSED_FLAME("home_discussed_flame"),
    HOME_SECTION("home_section"),
    SLIDER("slider/{${Argument.POST_ID}}"),
    COMMENTS("comments/{${Argument.POST_ID}}"),
    TAGS("tags/{${Argument.URL}}");

    companion object {
        fun build(route: Route, params: Map<String, String> = mapOf()): String {
            var value = route.value
            for (key in params.keys) {
                value = value.replace("{$key}", params[key].toString())
            }
            return value
        }
    }
}
