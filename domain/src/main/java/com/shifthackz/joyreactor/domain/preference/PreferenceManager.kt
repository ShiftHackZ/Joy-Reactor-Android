package com.shifthackz.joyreactor.domain.preference

import kotlinx.coroutines.flow.Flow


interface PreferenceManager {
    suspend fun getNsfwFilter(): Flow<Boolean>
    suspend fun setNsfwFilter(value: Boolean)
}
