package com.shifthackz.joyreactor.presentation.ui.screen.posts.search

import com.shifthackz.joyreactor.presentation.mvi.MviState

data class SearchState(
    val searchQuery: String = "",
): MviState
