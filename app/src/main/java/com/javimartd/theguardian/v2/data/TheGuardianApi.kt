package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.model.Section

interface TheGuardianApi {
    suspend fun getNews(sectionId: String): List<News>
    suspend fun getSections(): List<Section>
}