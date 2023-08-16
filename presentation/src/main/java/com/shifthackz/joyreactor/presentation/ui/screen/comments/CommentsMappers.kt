package com.shifthackz.joyreactor.presentation.ui.screen.comments

import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.presentation.entity.asTextUi

fun List<Comment>.toUiModel(): List<CommentsUiModel> {
    val groups = this
        .sortedWith(compareBy({ it.parentId }, { it.id }))
        .groupBy { it.parentId }

    fun follow(comment: Comment): CommentsUiModel {
        return CommentsUiModel(
            comment,
            (groups[comment.id] ?: emptyList()).map(::follow)
        )
    }

    return map { it.parentId }
        .subtract(map { it.id }.toSet())
        .flatMap { groups[it] ?: emptyList() }
        .map(::follow)
}

fun Result<List<Comment>>.toUiState(): CommentsState = fold(
    onSuccess = { comments ->
        CommentsState.Content(
            comments.toUiModel(),
            comments.size,
        )
    },
    onFailure = { t ->
        t.message.toString().asTextUi().let(CommentsState::Error)
    },
)
