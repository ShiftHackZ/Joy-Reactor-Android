package com.shifthackz.joyreactor.presentation.ui.screen.tags

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shifthackz.joyreactor.domain.usecase.tags.FetchTagsUseCase
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.mvi.MviStateViewModel
import com.shifthackz.joyreactor.presentation.ui.paging.TagsPagingSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow

class TagsViewModel(
    private val url: String,
    private val fetchTagsUseCase: FetchTagsUseCase,
) : MviStateViewModel<TagsState>() {


    private val config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
        enablePlaceholders = false,
    )

    private val pagers: List<Pager<String, Nsfw<Tag>>>

    val pagingFlows: List<Flow<PagingData<Nsfw<Tag>>>>

    override val emptyState = TagsState(url)

    init {
        TagsState(url).run {
            pagers = links.map { link ->
                Pager(
                    config = config,
                    initialKey = link.url,
                    pagingSourceFactory = { TagsPagingSource(fetchTagsUseCase, link.url) },
                )
            }
            pagingFlows =  pagers.map { it.flow.cachedIn(GlobalScope) }
        }
    }
}
