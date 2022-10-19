package com.javimartd.theguardian.v2.data.datasources.cache

import com.javimartd.theguardian.v2.domain.model.SectionEntity
import javax.inject.Inject

class NewsCacheDataSourceImpl @Inject constructor(): NewsCacheDataSource {

    private var sections = mutableListOf<SectionEntity>()

    override suspend fun getSections(): List<SectionEntity> {
        return sections.toList()
    }

    override suspend fun saveSections(sections: List<SectionEntity>) {
        this.sections.clear()
        this.sections.addAll(sections)
    }
}