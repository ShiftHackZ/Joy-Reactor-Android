package com.shifthackz.joyreactor.presentation.ui.screen.tags

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shifthackz.joyreactor.domain.usecase.tags.FetchTagsUseCase
import com.shifthackz.joyreactor.entity.Tag
import com.shifthackz.joyreactor.presentation.ui.paging.TagsPagingSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow

class TagsViewModel(
    private val url: String,
    private val fetchTagsUseCase: FetchTagsUseCase,
) : ViewModel() {


    private val config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
        enablePlaceholders = false,
    )

    private val pager: Pager<String, Tag> = Pager(
        config = config,
        initialKey = url,
        pagingSourceFactory = { TagsPagingSource(fetchTagsUseCase, url) },
    )

    val pagingFlow: Flow<PagingData<Tag>> = pager.flow.cachedIn(GlobalScope)

}