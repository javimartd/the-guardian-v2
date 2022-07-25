package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(): NewsLocalDataSource {

    private var sections = mutableListOf<SectionRaw>()

    override suspend fun getSections(): List<SectionRaw> {
        return sections.toList()
    }

    override suspend fun saveSections(sections: List<SectionRaw>) {
        this.sections.clear()
        this.sections.addAll(sections)
    }
}