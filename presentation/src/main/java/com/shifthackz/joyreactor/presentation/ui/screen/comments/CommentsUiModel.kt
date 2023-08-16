package com.shifthackz.joyreactor.presentation.ui.screen.comments

import com.shifthackz.joyreactor.entity.Comment

data class CommentsUiModel(
    val comment: Comment,
    val children: List<CommentsUiModel> = emptyList(),
)
