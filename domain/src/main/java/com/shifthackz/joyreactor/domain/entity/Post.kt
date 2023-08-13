package com.shifthackz.joyreactor.domain.entity

data class Post(
    val id: String,
    val author: Author,
    val contents: List<Content>,
    val tags: List<Tag>,
    val rating: Double,
)
