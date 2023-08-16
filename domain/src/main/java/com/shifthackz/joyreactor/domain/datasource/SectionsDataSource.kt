package com.shifthackz.joyreactor.domain.datasource

import com.shifthackz.joyreactor.entity.Section

sealed interface SectionsDataSource {

    interface Remote : SectionsDataSource {
        suspend fun fetchSections(): Result<List<Section>>
    }
}
