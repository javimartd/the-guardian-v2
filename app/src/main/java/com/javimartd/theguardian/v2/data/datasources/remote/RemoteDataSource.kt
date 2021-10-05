package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.Resource

interface RemoteDataSource {
    suspend fun getNews(sectionId: String): Resource<List<RawNews>>
    suspend fun getSections(): Resource<List<RawSection>>
}