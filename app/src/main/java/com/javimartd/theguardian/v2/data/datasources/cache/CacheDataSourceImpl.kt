package com.javimartd.theguardian.v2.data.datasources.cache

import com.javimartd.theguardian.v2.data.datasources.CacheDataSource
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(): CacheDataSource {

    private var _sections = Mutex()
    private var sections = mutableListOf<SectionData>()

    override suspend fun getSections(): List<SectionData> {
        return _sections.withLock { this.sections }
    }
    override suspend fun saveSections(sections: List<SectionData>) {
        _sections.withLock {
            this.sections.clear()
            this.sections.addAll(sections)
        }
    }
}