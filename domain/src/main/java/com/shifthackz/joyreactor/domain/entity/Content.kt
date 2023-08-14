package com.shifthackz.joyreactor.domain.entity

sealed interface Content {
    val type: Type

    data class Header(val text: String): Content {
        override val type = Type.TEXT
    }

    data class Text(val text: String): Content {
        override val type = Type.TEXT
    }

    data class Image(val url: String) : Content {
        override val type = Type.IMAGE
    }

    enum class Type {
        TEXT, IMAGE;
    }
}
