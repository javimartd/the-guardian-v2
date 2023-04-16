package com.javimartd.theguardian.v2.features.news

import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.factory.DomainFactory
import com.javimartd.theguardian.v2.features.news.mapper.toPresentation
import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState
import org.junit.Assert
import org.junit.Test

class NewsMapperTest {

    @Test
    fun newsToPresentation() {
        val domain = DomainFactory.getSomeNews(5)
        val presentation = domain.map { it.toPresentation() }
        assertEqual(domain[0], presentation[0])
    }

    @Test
    fun sectionsToPresentation() {
        val domain = DomainFactory.getSomeSections(5)
        val presentation = domain.map { it.webTitle }
        assertEqual(domain[0], presentation[0])
    }

    private fun assertEqual(entity: News, uiState: NewsItemUiState) {
        Assert.assertEquals(entity.body, uiState.body)
        Assert.assertEquals("11 Oct 2022", uiState.date)
        Assert.assertEquals(entity.thumbnail, uiState.thumbnail)
        Assert.assertEquals(entity.title, uiState.title)
        Assert.assertEquals(entity.webUrl, uiState.webUrl)
    }

    private fun assertEqual(entity: Section, ui: String) {
        Assert.assertEquals(entity.webTitle, ui)
    }
}