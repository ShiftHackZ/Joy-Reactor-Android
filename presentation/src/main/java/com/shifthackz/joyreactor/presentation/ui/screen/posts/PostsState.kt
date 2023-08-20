package com.shifthackz.joyreactor.presentation.ui.screen.posts

import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.mvi.MviState

data class PostsState(
    val toolbarUI: ToolbarUI = ToolbarUI(),
    val shouldRefresh: Boolean = false,
): MviState
