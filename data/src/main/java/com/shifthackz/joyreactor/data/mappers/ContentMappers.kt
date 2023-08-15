package com.shifthackz.joyreactor.data.mappers

import com.shifthackz.joyreactor.entity.Content
import com.shifthackz.joyreactor.storage.db.entity.ContentEntity

fun ContentEntity.toDomain(): Content = when (this.type) {
    Content.Identifier.VIDEO.id -> Content.Video(value)
    Content.Identifier.IMAGE.id -> Content.Image(value)
    Content.Identifier.H3.id -> Content.Header(value)
    else -> Content.Text(value)
}
