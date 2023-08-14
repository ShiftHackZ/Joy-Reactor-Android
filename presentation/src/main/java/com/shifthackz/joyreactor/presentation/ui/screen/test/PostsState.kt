package com.shifthackz.joyreactor.presentation.ui.screen.test

import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.mvi.MviState

data class PostsState(
    val toolbarUI: ToolbarUI = ToolbarUI(),
): MviState
