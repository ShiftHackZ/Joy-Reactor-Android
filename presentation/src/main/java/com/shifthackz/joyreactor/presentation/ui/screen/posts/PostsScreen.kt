@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.joyreactor.presentation.ui.screen.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.shifthackz.joyreactor.presentation.mvi.EmptyEffect
import com.shifthackz.joyreactor.presentation.mvi.MviScreen
import com.shifthackz.joyreactor.presentation.ui.theme.SetStatusBarColor
import com.shifthackz.joyreactor.presentation.ui.widget.PostComposable
import com.shifthackz.joyreactor.presentation.ui.widget.PostShimmer
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable
import kotlinx.coroutines.flow.Flow

class PostsScreen(
    private val viewModel: PostsViewModel,
    private val navigateBack: () -> Unit = {},
    private val postsActionsListener: PostsActionsListener,
) : MviScreen<PostsState, EmptyEffect>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
            pagingFlow = viewModel.pagingFlow,
            navigateBack = navigateBack,
            postsActionsListener = postsActionsListener,
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: PostsState,
    pagingFlow: Flow<PagingData<PostsUI>>,
    navigateBack: () -> Unit = {},
    postsActionsListener: PostsActionsListener,
) {
    SetStatusBarColor()
    val lazyPosts = pagingFlow.collectAsLazyPagingItems()
    val emptyStatePredicate: () -> Boolean = {
        lazyPosts.loadState.refresh is LoadState.NotLoading
                && lazyPosts.itemCount == 0
                && lazyPosts.loadState.append.endOfPaginationReached
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            ToolbarComposable(
                toolbarUI = state.toolbarUI,
                navigateBack = navigateBack,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = lazyPosts,
                key = { post -> post.key() },
            ) { post ->
                (post as? PostsUI.Ok)?.post?.let {
                    PostComposable(
                        post = it,
                        postsActionsListener = postsActionsListener,
                    )
                }
                (post as? PostsUI.NonUnique)?.let {
                    Box(Modifier.height(100.dp).background(Color.Cyan))
                }
            }
            lazyPosts.run {
                when {
//                emptyStatePredicate() -> items((0..3).toList()) {
//                    PostShimmer()
//                }
                    loadState.refresh is LoadState.Loading -> item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .verticalScroll(rememberScrollState()),
                        ) {
                            repeat(5) {
                                PostShimmer()
                            }
                        }
                    }
                }
            }
        }
    }
}
