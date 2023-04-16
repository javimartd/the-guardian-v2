package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.data.repository.news.model.SectionData

interface NewsCacheDataSource {
    suspend fun getSections(): List<SectionData>
    suspend fun saveSections(sections: List<SectionData>)
}