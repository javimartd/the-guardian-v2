package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.Resource

interface LocalDataSource {
    suspend fun getSections(): List<RawSection>
    suspend fun saveSections(sections: List<RawSection>)
}