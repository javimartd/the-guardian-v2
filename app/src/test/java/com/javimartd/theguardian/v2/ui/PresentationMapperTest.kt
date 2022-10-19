package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.data.DataFactory
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.ui.mapper.sectionsMapToView
import com.javimartd.theguardian.v2.ui.model.NewsUi
import org.junit.Assert
import org.junit.Test

class PresentationMapperTest {

    @Test
    fun newsToPresentation() {
        val data = DataFactory.getSomeNews(5)
        val presentation = data.()
        assertEqual(data[0], presentation[0])
    }

    @Test
    fun sectionsToPresentation() {
        val data = DataFactory.makeSections(5)
        val presentation = data.sectionsMapToView()
        assertEqual(data[0], presentation[0])
    }

    private fun assertEqual(data: NewsRaw, presentation: NewsUi) {
        Assert.assertEquals(data.webPublicationDate, presentation.date)
        Assert.assertEquals(data.webTitle, presentation.title)
        Assert.assertEquals(data.webUrl, presentation.webUrl)
        Assert.assertEquals(data.fields?.thumbnail, presentation.thumbnail)
        Assert.assertEquals(data.fields?.bodyText, presentation.description)
    }

    private fun assertEqual(data: SectionRaw, presentation: String) {
        Assert.assertEquals(data.webTitle, presentation)
    }
}