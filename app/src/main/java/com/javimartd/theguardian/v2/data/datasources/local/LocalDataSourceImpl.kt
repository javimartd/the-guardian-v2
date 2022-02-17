package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(): LocalDataSource {

    private var sections = mutableListOf<RawSection>()

    override suspend fun getSections(): List<RawSection> {
        return sections.toList()
    }

    override suspend fun saveSections(sections: List<RawSection>) {
        this.sections.clear()
        this.sections.addAll(sections)
    }
}