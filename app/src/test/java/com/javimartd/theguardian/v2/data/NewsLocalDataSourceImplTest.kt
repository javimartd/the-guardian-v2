package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsLocalDataSourceImplTest: TestCase() {

    private lateinit var sut : NewsLocalDataSource

    @Before
    fun setup() {
        sut = NewsLocalDataSourceImpl()
    }

    @Test
    fun `get sections`()
    = runBlocking {

        // when
        val actual = sut.getSections()

        // then
        Assert.assertEquals(actual, emptyList<SectionRaw>())
    }

    @Test
    fun `save sections`() {

        // given
        val data = DataFactory.makeSections(2)

        runBlocking {
            // when
            sut.saveSections(data)
            val actual = sut.getSections()

            // then
            Assert.assertEquals(2, actual.size)
            MatcherAssert.assertThat(actual[0], IsInstanceOf.instanceOf(SectionRaw::class.java))
        }
    }
}