package com.shifthackz.joyreactor.network.extensions

fun String.formatImageUrl(): String {
    return if (startsWith("//")) "https:$this" else this
}