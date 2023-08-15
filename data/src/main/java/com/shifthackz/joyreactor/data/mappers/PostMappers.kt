package com.shifthackz.joyreactor.data.mappers

import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.storage.db.entity.ContentEntity
import com.shifthackz.joyreactor.storage.db.entity.PostEntity

fun Post.toEntity(): PostEntity = with(this) {
    PostEntity(
        id = id,
        rating = rating,
        authorName = author.name,
    )
}

fun Post.toContentEntities(): List<ContentEntity> = contents.mapIndexed { index, content ->
    ContentEntity(
        id = "${id}_${index}",
        type = content.identifier.id,
        value = when (content) {
            is Content.Header -> content.text
            is Content.Image -> content.url
            is Content.Text -> content.text
        },
        postId = id,
    )
}
