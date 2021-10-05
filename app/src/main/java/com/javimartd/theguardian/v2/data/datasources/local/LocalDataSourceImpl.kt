package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.Resource

class LocalDataSourceImpl: LocalDataSource {

    private var sections = mutableListOf<RawSection>()

    override suspend fun getSections(): Resource<List<RawSection>> {
        return Resource.Success(sections.toList())
    }

    override suspend fun saveSections(sections: List<RawSection>) {
        this.sections.clear()
        this.sections.addAll(sections)
    }
}