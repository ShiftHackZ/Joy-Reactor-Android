package com.shifthackz.joyreactor.entity

sealed interface Content {
    val identifier: Identifier
    val type: Type

    data class Header(val text: String): Content {
        override val identifier = Identifier.H3
        override val type = Type.TEXT
    }

    data class Text(val text: String): Content {
        override val identifier = Identifier.TEXT
        override val type = Type.TEXT
    }

    data class Image(val url: String) : Content {
        override val identifier: Identifier = Identifier.IMAGE
        override val type = Type.IMAGE
    }

    enum class Type {
        TEXT, IMAGE;
    }

    enum class Identifier(val id: String) {
        H3("h3"),
        TEXT("text"),
        IMAGE("image"),
    }
}
