package com.shifthackz.joyreactor.presentation.ui.screen.comments

import com.shifthackz.joyreactor.presentation.mvi.MviState

sealed interface CommentsState : MviState {
    object Loading : CommentsState
    data class Content(val comments: List<CommentsUiModel>): CommentsState
}
