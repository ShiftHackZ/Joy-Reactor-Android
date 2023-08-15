package com.shifthackz.joyreactor.domain.usecase

import com.shifthackz.joyreactor.entity.PagePayload
import com.shifthackz.joyreactor.entity.Post

interface GetPostsPageUseCase {
    suspend operator fun invoke(url: String): PagePayload<Post>
}
