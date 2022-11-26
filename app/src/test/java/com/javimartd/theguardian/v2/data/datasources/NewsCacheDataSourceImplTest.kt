package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.DomainFactory
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
class NewsCacheDataSourceImplTest: TestCase() {

    private lateinit var sut : NewsCacheDataSource

    @Before
    fun setup() {
        sut = NewsCacheDataSourceImpl()
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
        val data = DomainFactory.getSomeSections(2)

        runBlocking {

            // when
            sut.saveSections(data)
            val actual = sut.getSections()

            // then
            Assert.assertEquals(2, actual.size)
            MatcherAssert.assertThat(actual[0], IsInstanceOf.instanceOf(SectionEntity::class.java))
        }
    }
}