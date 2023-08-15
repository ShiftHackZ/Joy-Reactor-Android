package com.shifthackz.joyreactor.domain.usecase.post

import com.shifthackz.joyreactor.entity.Post

interface GetFullPostUseCase {
    suspend operator fun invoke(id: String): Post
}
