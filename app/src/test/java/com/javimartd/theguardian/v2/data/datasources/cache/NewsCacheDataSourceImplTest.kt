package com.javimartd.theguardian.v2.data.datasources.cache

import com.javimartd.theguardian.v2.data.datasources.remote.news.model.SectionRaw
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.DomainFactory
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class NewsCacheDataSourceImplTest {

    private val sut = NewsCacheDataSourceImpl()

    @Test
    fun `get sections`()
    = runTest {

        // when
        val actual = sut.getSections()

        // then
        Assert.assertEquals(actual, emptyList<SectionRaw>())
    }

    @Test
    fun `save sections`() {

        // given
        val data = DomainFactory.getSomeSections(2)

        runTest {

            // when
            sut.saveSections(data)
            val actual = sut.getSections()

            // then
            Assert.assertEquals(2, actual.size)
            MatcherAssert.assertThat(actual[0], IsInstanceOf.instanceOf(SectionEntity::class.java))
        }
    }
}