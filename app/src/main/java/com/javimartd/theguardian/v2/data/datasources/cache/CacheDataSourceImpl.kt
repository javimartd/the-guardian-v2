package com.javimartd.theguardian.v2.data.datasources.cache

import com.javimartd.theguardian.v2.data.datasources.CacheDataSource
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(): CacheDataSource {

    private var sections = mutableListOf<SectionData>()

    override suspend fun getSections(): List<SectionData> {
        return sections.toList()
    }

    override suspend fun saveSections(sections: List<SectionData>) {
        this.sections.clear()
        this.sections.addAll(sections)
    }
}