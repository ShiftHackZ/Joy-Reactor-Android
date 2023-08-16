package com.shifthackz.joyreactor.data.datasource.remote

import com.shifthackz.joyreactor.domain.datasource.SectionsDataSource
import com.shifthackz.joyreactor.entity.JoyReactorLink
import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.network.parser.SectionsParser

internal class SectionsRemoteDataSource(
    private val sectionsParser: SectionsParser,
) : SectionsDataSource.Remote {

    override suspend fun fetchSections(): Result<List<Section>> {
        return sectionsParser.fetchSections(JoyReactorLink.HOME_GOOD.url)
    }
}
