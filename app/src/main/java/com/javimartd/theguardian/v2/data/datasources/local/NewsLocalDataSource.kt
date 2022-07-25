package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw

interface NewsLocalDataSource {
    suspend fun getSections(): List<SectionRaw>
    suspend fun saveSections(sections: List<SectionRaw>)
}