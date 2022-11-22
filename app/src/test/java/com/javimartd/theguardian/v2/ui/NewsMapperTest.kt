package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.DomainFactory
import org.junit.Assert
import org.junit.Test

class NewsMapperTest {

    /*@Test
    fun newsToPresentation() {
        val entities = DomainFactory.getSomeNews(5)
        val presentation = entities.map { it.toPresentation() }
        assertEqual(entities[0], presentation[0])
    }

    @Test
    fun sectionsToPresentation() {
        val entities = DomainFactory.getSomeSections(5)
        val presentation = entities.map { it.webTitle }
        assertEqual(entities[0], presentation[0])
    }

    private fun assertEqual(entity: NewsEntity, uiState: NewsItemUiState) {
        Assert.assertEquals(entity.body, uiState.body)
        Assert.assertEquals(entity.date, uiState.date)
        Assert.assertEquals(entity.thumbnail, uiState.thumbnail)
        Assert.assertEquals(entity.title, uiState.title)
        Assert.assertEquals(entity.webUrl, uiState.webUrl)
    }

    private fun assertEqual(entity: SectionEntity, ui: String) {
        Assert.assertEquals(entity.webTitle, ui)
    }*/
}