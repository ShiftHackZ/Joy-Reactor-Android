@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.shifthackz.joyreactor.presentation.ui.screen.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.shifthackz.joyreactor.presentation.extensions.rememberLazyListState
import com.shifthackz.joyreactor.presentation.mvi.EmptyEffect
import com.shifthackz.joyreactor.presentation.mvi.MviScreen
import com.shifthackz.joyreactor.presentation.ui.theme.SetStatusBarColor
import com.shifthackz.joyreactor.presentation.ui.widget.PostComposable
import com.shifthackz.joyreactor.presentation.ui.widget.PostShimmer
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable

class PostsScreen(
    private val viewModel: PostsViewModel,
    private val navigateBack: () -> Unit = {},
    private val postsActionsListener: PostsActionsListener,
) : MviScreen<PostsState, EmptyEffect>(viewModel) {

    @Composable
    override fun Content() {
        PostsScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = viewModel.state.collectAsStateWithLifecycle().value,
            lazyPosts = viewModel.pagingFlow.collectAsLazyPagingItems(),
            navigateBack = navigateBack,
            postsActionsListener = postsActionsListener,
        )
    }
}

@Composable
fun PostsScreenContent(
    modifier: Modifier = Modifier,
    state: PostsState = PostsState(),
    lazyPosts: LazyPagingItems<PostsUI>,
    navigateBack: () -> Unit = {},
    postsActionsListener: PostsActionsListener,
) {
    SetStatusBarColor()
    val listState = lazyPosts.rememberLazyListState()
//    val emptyStatePredicate: () -> Boolean = {
//        lazyPosts.loadState.refresh is LoadState.NotLoading
//                && lazyPosts.itemCount == 0
//                && lazyPosts.loadState.append.endOfPaginationReached
//    }
    val isRefreshing = lazyPosts.loadState.refresh is LoadState.Loading && lazyPosts.itemCount > 0
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { lazyPosts.refresh() },
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            ToolbarComposable(
                toolbarUI = state.toolbarUI,
                navigateBack = navigateBack,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .padding(paddingValues),
        ) {
            LazyColumn(
                state = listState,
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
            PullRefreshIndicator(
                isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
        }
    }
}
