package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
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
class LocalDataSourceImplTest: TestCase() {

    private lateinit var sut : LocalDataSource

    @Before
    fun setup() {
        sut = LocalDataSourceImpl()
    }

    @Test
    fun `get sections`()
    = runBlocking {

        // when
        val actual = sut.getSections()

        // then
        Assert.assertEquals(actual, emptyList<RawSection>())
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
            MatcherAssert.assertThat(actual[0], IsInstanceOf.instanceOf(RawSection::class.java))
        }
    }
}