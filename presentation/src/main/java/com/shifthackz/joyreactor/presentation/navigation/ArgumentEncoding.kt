package com.shifthackz.joyreactor.presentation.navigation

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encodeNavArg(): String {
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}

fun String.decodeNavArg(): String {
    return URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
}
