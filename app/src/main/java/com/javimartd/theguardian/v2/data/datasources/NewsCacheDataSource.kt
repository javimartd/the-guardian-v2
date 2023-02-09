package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.domain.model.SectionEntity

interface NewsCacheDataSource {
    suspend fun getSections(): List<SectionEntity>
    suspend fun saveSections(sections: List<SectionEntity>)
}