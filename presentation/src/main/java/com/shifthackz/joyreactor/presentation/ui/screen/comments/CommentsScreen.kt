@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shifthackz.joyreactor.entity.Author
import com.shifthackz.joyreactor.entity.Comment
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.TextUI
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.mvi.MviStateScreen
import com.shifthackz.joyreactor.presentation.ui.widget.CommentComposable
import com.shifthackz.joyreactor.presentation.ui.widget.CommentShimmer
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable

class CommentsScreen(
    private val viewModel: CommentsViewModel,
    private val navigateBack: () -> Unit = {},
) : MviStateScreen<CommentsState>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
            navigateBack = navigateBack,
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: CommentsState,
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            ToolbarComposable(
                toolbarUI = ToolbarUI(
                    backButton = true,
                    title = TextUI.Concat(
                        R.string.comments_title,
                        (state as? CommentsState.Content)?.totalCount?.let { ": $it"} ?: "",
                    ),
                ),
                navigateBack = navigateBack,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            when (state) {
                is CommentsState.Content -> state.comments.forEach { comment ->
                    CommentComposable(commentUi = comment)
                }
                is CommentsState.Error -> {}
                CommentsState.Loading -> repeat(10) {
                    CommentShimmer()
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewStateContent() {
    val author = Author("User Name", "https://img2.joyreactor.cc/images/default_avatar_50.gif")
    ScreenContent(
        state = CommentsState.Content(
            comments = listOf(
                CommentsUiModel(
                    Comment(
                        "1",
                        null,
                        "Hello",
                        author,
                        rating = 0.0,
                    ),
                    listOf(
                        CommentsUiModel(
                            Comment(
                                "1",
                                null,
                                "Hello",
                                author,
                                rating = 0.0,
                            ),
                            listOf(

                            ),
                        )
                    ),
                )
            ),
            totalCount = 2,
        )
    )
}

@Composable
@Preview
private fun PreviewStateLoading() {
    ScreenContent(state = CommentsState.Loading)
}