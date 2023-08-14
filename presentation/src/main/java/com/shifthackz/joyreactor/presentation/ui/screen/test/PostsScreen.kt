package com.shifthackz.joyreactor.presentation.ui.screen.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.shifthackz.joyreactor.domain.entity.Post
import com.shifthackz.joyreactor.presentation.mvi.EmptyEffect
import com.shifthackz.joyreactor.presentation.mvi.EmptyState
import com.shifthackz.joyreactor.presentation.mvi.MviScreen
import com.shifthackz.joyreactor.presentation.ui.widget.PostComposable
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable
import kotlinx.coroutines.flow.Flow

class PostsScreen(
    private val viewModel: PostsViewModel,
    private val navigateBack: () -> Unit = {},
    private val openPosts: (String) -> Unit = {},
) : MviScreen<PostsState, EmptyEffect>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
            pagingFlow = viewModel.pagingFlow,
            navigateBack = navigateBack,
            openPosts = openPosts,
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: PostsState,
    pagingFlow: Flow<PagingData<Post>>,
    navigateBack: () -> Unit = {},
    openPosts: (String) -> Unit = {},
) {
    val lazyPosts = pagingFlow.collectAsLazyPagingItems()
    Scaffold(
        modifier = modifier,
        topBar = {
            ToolbarComposable(
                toolbarUI = state.toolbarUI,
                onNavigateBack = navigateBack,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(lazyPosts) { post ->
                post?.let {
                    PostComposable(post = it, openPosts = openPosts)
                }
            }
        }
    }
}
