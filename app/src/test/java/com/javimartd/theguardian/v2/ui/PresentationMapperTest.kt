package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.data.DataFactory
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.ui.mapper.newsMapToView
import com.javimartd.theguardian.v2.ui.mapper.sectionsMapToView
import com.javimartd.theguardian.v2.ui.model.News
import org.junit.Assert
import org.junit.Test

class PresentationMapperTest {

    @Test
    fun newsMapToPresentation() {
        val data = DataFactory.makeNews(5)
        val presentation = data.newsMapToView()
        assertEqual(data[0], presentation[0])
    }

    @Test
    fun sectionsMapToPresentation() {
        val data = DataFactory.makeSections(5)
        val presentation = data.sectionsMapToView()
        assertEqual(data[0], presentation[0])
    }

    private fun assertEqual(data: RawNews, presentation: News) {
        Assert.assertEquals(data.id, presentation.id)
        Assert.assertEquals(data.sectionId, presentation.sectionId)
        Assert.assertEquals(data.sectionName, presentation.sectionName)
        Assert.assertEquals(data.webPublicationDate, presentation.date)
        Assert.assertEquals(data.webTitle, presentation.title)
        Assert.assertEquals(data.webUrl, presentation.webUrl)
        Assert.assertEquals(data.fields?.thumbnail, presentation.thumbnail)
        Assert.assertEquals(data.fields?.bodyText, presentation.description)
    }

    private fun assertEqual(data: RawSection, presentation: String) {
        Assert.assertEquals(data.webTitle, presentation)
    }
}