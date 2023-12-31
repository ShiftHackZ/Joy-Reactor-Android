package com.shifthackz.joyreactor.network.extensions

import java.net.URL

fun String.formatImageUrl(): String {
    return if (startsWith("//")) "https:$this" else this
}

fun String.formatSectionUrl(): String {
    if (startsWith("javascript")) return ""
    return if (startsWith("http")) this else "https://joyreactor.cc$this"
}

fun String.baseUrl(): String {
    val locationUrl = URL(this)
    return locationUrl.protocol + "://" + locationUrl.host
}

fun String.initUrl(): String {
    val cc  =(if (!this.startsWith("http")) "https://joyreactor.cc$this"
    else this)

    return cc.replace("#", "%23").replace(" ", "+")
}
