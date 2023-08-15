package com.shifthackz.joyreactor.data.mappers

import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.entity.Post
import com.shifthackz.joyreactor.storage.db.dto.FullPostDto
import com.shifthackz.joyreactor.storage.db.entity.ContentEntity
import com.shifthackz.joyreactor.storage.db.entity.PostEntity
import com.shifthackz.joyreactor.storage.db.entity.TagEntity

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
            is Content.Video -> content.url
        },
        postId = id,
    )
}

fun FullPostDto.toDomain(): Post = with(this) {
    Post(
        id = post.id,
        author = author.toDomain(),
        contents = contents.map(ContentEntity::toDomain),
        tags = tags.map(TagEntity::toDomain),
        rating = post.rating,
        comments = emptyList(),
    )
}
