package com.shifthackz.joyreactor.domain.usecase

import com.shifthackz.joyreactor.entity.Post

interface GetFullPostUseCase {
    suspend operator fun invoke(id: String): Post
}
