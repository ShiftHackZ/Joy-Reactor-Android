package com.shifthackz.joyreactor.presentation.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shifthackz.joyreactor.domain.usecase.tags.FetchTagsUseCase
import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag

class TagsPagingSource(
    private val fetchTagsUseCase: FetchTagsUseCase,
    private val firstKey: String,
) : PagingSource<String, Nsfw<Tag>>() {

    override fun getRefreshKey(state: PagingState<String, Nsfw<Tag>>): String? {
        return firstKey
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Nsfw<Tag>> {
        val key = params.key ?: firstKey
        return fetchTagsUseCase(key).let { result ->
            result.fold(
                onSuccess = { page ->
                    LoadResult.Page(
                        data = page.data,
                        prevKey = page.prev,
                        nextKey = page.next
                    )
                },
                onFailure = { t -> LoadResult.Error(t) },
            )
        }
    }
}
