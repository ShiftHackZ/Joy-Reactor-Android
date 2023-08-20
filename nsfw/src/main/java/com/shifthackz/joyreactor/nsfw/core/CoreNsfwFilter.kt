package com.shifthackz.joyreactor.nsfw.core

import com.shifthackz.joyreactor.domain.nsfw.NsfwFilter
import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import com.shifthackz.joyreactor.entity.Nsfw
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal abstract class CoreNsfwFilter<T : Any>(
    private val preferenceManager: PreferenceManager,
) : NsfwFilter<T> {

    override suspend fun nsfwEnabled(): Flow<Boolean> {
        return preferenceManager.getNsfwFilter()
    }

    override suspend fun filter(data: List<T>): List<Nsfw<T>> = coroutineScope {
        val enabled = nsfwEnabled().first()
        if (enabled) evaluate(data)
        else data.map { Nsfw.Safe(it) }
    }

    abstract suspend fun evaluate(data: List<T>): List<Nsfw<T>>
}
