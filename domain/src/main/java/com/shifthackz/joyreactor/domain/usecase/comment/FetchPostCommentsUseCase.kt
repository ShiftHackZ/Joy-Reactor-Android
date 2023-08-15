package com.shifthackz.joyreactor.domain.usecase.comment

import com.shifthackz.joyreactor.entity.Comment

interface FetchPostCommentsUseCase {
    suspend operator fun invoke(postId: String): List<Comment>
}
