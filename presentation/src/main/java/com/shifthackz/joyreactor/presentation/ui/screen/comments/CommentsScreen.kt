package com.shifthackz.joyreactor.presentation.ui.screen.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shifthackz.joyreactor.presentation.mvi.MviStateScreen

class CommentsScreen(
    private val viewModel: CommentsViewModel,
) : MviStateScreen<CommentsState>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: CommentsState,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            (state as? CommentsState.Content)?.let { cnt ->

                @Composable
                fun CommentComposable(
                    modifier: Modifier = Modifier,
                    commentUi: CommentsUiModel,
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()

                            .padding(all = 8.dp),
                        ) {
                        Text(
                            text = commentUi.comment.text
                        )
                        commentUi.children.forEach {
                            CommentComposable(modifier.padding(start = 16.dp), commentUi = it)
                        }
                    }
                }

                cnt.comments.forEach {
                    CommentComposable(commentUi = it)
                }
            }
        }
    }
}