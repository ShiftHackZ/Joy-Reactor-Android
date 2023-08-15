package com.shifthackz.joyreactor.entity

enum class JoyReactorLink(val url: String, val isRootLink: Boolean = false) {
    HOME_NEW("https://joyreactor.cc/all", true),
    HOME_GOOD("https://joyreactor.cc", true),
    HOME_BEST("https://joyreactor.cc/best", true),
    DISCUSSED_ALL("https://joyreactor.cc/discussion/all", true),
    DISCUSSED_GOOD("https://joyreactor.cc/discussion", true),
    DISCUSSED_FLAME("https://joyreactor.cc/discussion/flame", true),
}
