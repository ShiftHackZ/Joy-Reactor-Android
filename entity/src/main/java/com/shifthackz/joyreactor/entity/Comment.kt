package com.shifthackz.joyreactor.entity

data class Comment(
    val id: String,
    val parentId: String?,
    val text: String,
    val author: Author,
    val rating: Double,
)
