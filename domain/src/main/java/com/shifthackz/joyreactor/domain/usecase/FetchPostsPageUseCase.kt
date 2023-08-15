package com.shifthackz.joyreactor.domain.usecase

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

interface FetchPostsPageUseCase {
    suspend operator fun invoke(url: String): PagePayload<Post>
}
