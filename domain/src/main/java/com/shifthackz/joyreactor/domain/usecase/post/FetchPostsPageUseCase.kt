package com.shifthackz.joyreactor.domain.usecase.post

import com.shifthackz.joyreactor.entity.Nsfw
import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

interface FetchPostsPageUseCase {
    suspend operator fun invoke(url: String): Result<PagePayload<Nsfw<Post>>>
}
