@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
)

package com.shifthackz.joyreactor.presentation.ui.screen.tags

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.R
import com.shifthackz.joyreactor.presentation.entity.ToolbarUI
import com.shifthackz.joyreactor.presentation.entity.asTextUi
import com.shifthackz.joyreactor.presentation.extensions.rememberLazyListState
import com.shifthackz.joyreactor.presentation.mvi.MviStateScreen
import com.shifthackz.joyreactor.presentation.ui.screen.posts.PostsActionsListener
import com.shifthackz.joyreactor.presentation.ui.widget.ChipComposable
import com.shifthackz.joyreactor.presentation.ui.widget.TagComposable
import com.shifthackz.joyreactor.presentation.ui.widget.TagShimmer
import com.shifthackz.joyreactor.presentation.ui.widget.ToolbarComposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TagsScreen(
    private val viewModel: TagsViewModel,
    private val postsActionsListener: PostsActionsListener = PostsActionsListener.empty,
    private val navigateBack: () -> Unit = {},
) : MviStateScreen<TagsState>(viewModel) {

    @Composable
    override fun Content() {
        ScreenContent(
            modifier = Modifier.fillMaxSize(),
            pagingFlows = viewModel.pagingFlows,
            state = viewModel.state.collectAsStateWithLifecycle().value,
            postsActionsListener = postsActionsListener,
            navigateBack = navigateBack,
        )
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    pagingFlows: List<Flow<PagingData<Nsfw<Tag>>>>,
    state: TagsState,
    postsActionsListener: PostsActionsListener = PostsActionsListener.empty,
    navigateBack: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { state.links.size }
    Scaffold(
        modifier = modifier,
        topBar = {
            ToolbarComposable(
                toolbarUI = ToolbarUI(backButton = true),
                navigateBack = navigateBack,
                actions = {
                    state.links.forEachIndexed { index, link ->
                        ChipComposable(
                            modifier = Modifier.clickable {
                                scope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            },
                            selected = pagerState.currentPage == index,
                            text = when (link) {
                                is TagsState.Link.Best -> R.string.feed_tab_best
                                is TagsState.Link.New -> R.string.feed_tab_new
                            }.asTextUi(),
                        )
                        Box(modifier = Modifier.width(8.dp))
                    }
                }
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,

        ) { index ->
            val lazyTags = pagingFlows[index].collectAsLazyPagingItems()
            val listState = lazyTags.rememberLazyListState()
            val isRefreshing = lazyTags.loadState.refresh is LoadState.Loading && lazyTags.itemCount > 0
            val pullRefreshState = rememberPullRefreshState(
                refreshing = isRefreshing,
                onRefresh = { lazyTags.refresh() },
            )
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
                        items = lazyTags,
                        key = { tag -> System.currentTimeMillis() + tag.hashCode() },
                    ) { tag ->
                        (tag as? Nsfw.Safe<Tag>)?.data?.let {
                            TagComposable(
                                modifier = Modifier.fillMaxWidth(),
                                tag = it,
                                onClick = { postsActionsListener.openPosts(it.url) }
                            )
                        }
                    }
                    lazyTags.run {
                        when {
                            loadState.refresh is LoadState.Loading -> items(5) {
                                TagShimmer(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                    item { Box(modifier = Modifier.height(100.dp)) }
                }
                PullRefreshIndicator(
                    isRefreshing,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter),
                )
            }
        }
    }
}
