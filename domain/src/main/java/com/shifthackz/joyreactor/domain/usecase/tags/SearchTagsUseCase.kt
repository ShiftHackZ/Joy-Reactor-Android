package com.shifthackz.joyreactor.domain.usecase.tags

import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.Tag

interface SearchTagsUseCase {
    suspend operator fun invoke(query: String): Result<List<Nsfw<Tag>>>
}
