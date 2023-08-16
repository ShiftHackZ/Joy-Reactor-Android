package com.shifthackz.joyreactor.domain.repository

import com.shifthackz.joyreactor.entity.Section

interface SectionsRepository {
    suspend fun fetchSections(): Result<List<Section>>
}
