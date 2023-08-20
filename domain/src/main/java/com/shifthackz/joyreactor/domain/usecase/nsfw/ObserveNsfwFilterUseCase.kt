package com.shifthackz.joyreactor.domain.usecase.nsfw

import kotlinx.coroutines.flow.Flow

interface ObserveNsfwFilterUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}
