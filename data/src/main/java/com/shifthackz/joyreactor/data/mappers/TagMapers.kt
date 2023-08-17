package com.shifthackz.joyreactor.data.mappers

import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.storage.db.entity.TagEntity

fun Tag.toEntity(): TagEntity = with(this) {
    TagEntity(
        name = name,
        url = url,
        imageUrl = imageUrl,
    )
}

fun TagEntity.toDomain(): Tag = with(this) {
    Tag(
        name = name,
        url = url,
        imageUrl = imageUrl,
    )
}
