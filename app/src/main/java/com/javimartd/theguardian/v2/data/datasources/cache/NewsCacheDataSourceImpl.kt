package com.javimartd.theguardian.v2.data.datasources.cache

import com.javimartd.theguardian.v2.data.datasources.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.domain.news.model.Section
import javax.inject.Inject

class NewsCacheDataSourceImpl @Inject constructor(): NewsCacheDataSource {

    private var sections = mutableListOf<SectionData>()

    override suspend fun getSections(): List<SectionData> {
        return sections.toList()
    }

    override suspend fun saveSections(sections: List<SectionData>) {
        this.sections.clear()
        this.sections.addAll(sections)
    }
}