package com.javimartd.theguardian.v2.data


import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.Resource

interface Repository {
    suspend fun getNews(sectionId: String): Resource<List<RawNews>>
    suspend fun getSections(): Resource<List<RawSection>>
}