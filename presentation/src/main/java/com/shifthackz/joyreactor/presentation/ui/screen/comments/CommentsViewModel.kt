package com.shifthackz.joyreactor.presentation.ui.screen.comments

import androidx.lifecycle.viewModelScope
import com.shifthackz.joyreactor.domain.usecase.comment.FetchPostCommentsUseCase
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val postId: String,
    private val fetchPostCommentsUseCase: FetchPostCommentsUseCase,
) : MviStateViewModel<CommentsState>() {

    override val emptyState = CommentsState.Loading

    init {
        viewModelScope.launch {
            fetchPostCommentsUseCase(postId)
                .let(List<Comment>::toUiModel)
                .let(CommentsState::Content)
                .let(::setState)
        }
    }
}
