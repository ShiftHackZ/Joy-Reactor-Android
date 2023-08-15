package com.shifthackz.joyreactor.entity

sealed interface Content {
    val identifier: Identifier
    val type: Type
    val value: String

    data class Header(val text: String): Content {
        override val identifier = Identifier.H3
        override val type = Type.TEXT
        override val value: String = text
    }

    data class Text(val text: String): Content {
        override val identifier = Identifier.TEXT
        override val type = Type.TEXT
        override val value: String = text
    }

    data class Image(val url: String) : Content {
        override val identifier: Identifier = Identifier.IMAGE
        override val type = Type.MEDIA
        override val value: String = url
    }

    data class Video(val url: String) : Content {
        override val identifier: Identifier = Identifier.VIDEO
        override val type = Type.MEDIA
        override val value: String = url
    }

    enum class Type {
        TEXT, MEDIA;
    }

    enum class Identifier(val id: String) {
        H3("h3"),
        TEXT("text"),
        IMAGE("image"),
        VIDEO("video"),
    }
}
