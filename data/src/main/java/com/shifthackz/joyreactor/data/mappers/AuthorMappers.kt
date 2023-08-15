package com.shifthackz.joyreactor.data.mappers

import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.storage.db.entity.AuthorEntity

fun Author.toEntity(): AuthorEntity = with(this) {
    AuthorEntity(
        name = name,
        avatarUrl = avatarUrl,
    )
}

fun AuthorEntity.toDomain(): Author = with(this) {
    Author(
        name = name,
        avatarUrl = avatarUrl,
    )
}
