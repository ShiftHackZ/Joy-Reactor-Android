package com.shifthackz.joyreactor.presentation.ui.screen.tags

import com.shifthackz.joyreactor.presentation.mvi.MviState

data class TagsState(
    val links: List<Link> = emptyList(),
): MviState {

    constructor(url: String) : this(buildList {
        if (url.endsWith("/rating")) {
            add(Link.Best(url))
            add(Link.New(url.replace("/rating", "/subtags")))
        }
    })

    sealed interface Link {
        val url: String

        data class Best(override val url: String): Link
        data class New(override val url: String): Link
    }
}
