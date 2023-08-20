package com.shifthackz.joyreactor.domain.nsfw

import com.shifthackz.joyreactor.entity.Nsfw
import kotlinx.coroutines.flow.Flow

interface NsfwFilter<T : Any> {

    suspend fun nsfwEnabled(): Flow<Boolean>

    suspend fun filter(data: List<T>): List<Nsfw<T>>
}
