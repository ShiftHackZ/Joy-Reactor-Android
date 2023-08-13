package com.shifthackz.joyreactor.domain.entity

data class PagePayload<T: Any>(
    val data: List<T>,
    val next: String? = null,
    val prev: String? = null,
)
