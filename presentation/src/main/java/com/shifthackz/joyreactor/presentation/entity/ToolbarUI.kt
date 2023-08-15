@file:Suppress("MemberVisibilityCanBePrivate")

package com.shifthackz.joyreactor.presentation.entity

import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.presentation.R

data class ToolbarUI constructor(
    val backButton: Boolean = false,
    val title: TextUI = TextUI.empty,
) {
    val isEmpty: Boolean
        get() = this == ToolbarUI()

    val isNotEmpty: Boolean
        get() = !isEmpty

    companion object {
        fun fromUrl(url: String): ToolbarUI = when {
            url.contains("/tag") -> ToolbarUI(
                backButton = true,
                title = TextUI.Resource(
                    R.string.toolbar_title_tag,
                    url.split("/").lastOrNull() ?: "",
                ),
            )
            else -> ToolbarUI(
                backButton = !JoyReactorLink.values().filter(JoyReactorLink::isRootLink).any { it.url.contains(url) }
            )
        }
    }
}
