package com.shifthackz.joyreactor.entity

import java.util.Date

data class Post(
    val id: String,
    val author: Author,
    val contents: List<Content>,
    val tags: List<Tag>,
    val rating: Double,
    val comments: List<Comment>,
    val estimatedCommentsCount: Int,
    val date: Date,
    val url: String,
)
