package com.shifthackz.joyreactor.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.shifthackz.joyreactor.domain.preference.PreferenceManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PreferenceManagerImpl(
    private val dataStore: DataStore<Preferences>,
) : PreferenceManager {

    override suspend fun getNsfwFilter(): Flow<Boolean> {
        return dataStore.data.map { prefs -> prefs[fieldNsfw] ?: true }
    }

    override suspend fun setNsfwFilter(value: Boolean): Unit = coroutineScope {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                set(fieldNsfw, value)
            }
        }
    }

    companion object {
        val fieldNsfw = booleanPreferencesKey("key_nsfw")
    }
}
