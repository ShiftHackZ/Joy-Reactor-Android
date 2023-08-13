package com.shifthackz.joyreactor.domain.entity

sealed interface Content {
    data class Image(val url: String) : Content
}
