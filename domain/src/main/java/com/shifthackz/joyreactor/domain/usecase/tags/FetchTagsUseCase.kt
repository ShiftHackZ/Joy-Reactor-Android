package com.shifthackz.joyreactor.domain.usecase.tags

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Tag

interface FetchTagsUseCase {
    suspend operator fun invoke(url: String): Result<PagePayload<Tag>>
}
