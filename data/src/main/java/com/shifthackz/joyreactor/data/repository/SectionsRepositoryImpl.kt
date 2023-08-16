package com.shifthackz.joyreactor.data.repository

import com.shifthackz.joyreactor.domain.datasource.SectionsDataSource
import com.shifthackz.joyreactor.domain.repository.SectionsRepository
import com.shifthackz.joyreactor.entity.Section

internal class SectionsRepositoryImpl(
    private val sectionsRds: SectionsDataSource.Remote,
) : SectionsRepository {

    override suspend fun fetchSections(): Result<List<Section>> {
        return sectionsRds.fetchSections()
    }
}
