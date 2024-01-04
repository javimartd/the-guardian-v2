package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.datasources.CacheDataSource
import com.javimartd.theguardian.v2.data.datasources.DiskDataSource
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.repository.NewsRepositoryImpl
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.factory.RepositoryFactory
import com.javimartd.theguardian.v2.utils.TestDefaultDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class NewsRepositoryImplTest {

    private lateinit var sut : NewsRepository

    @Mock private lateinit var remoteDataSource: RemoteDataSource
    @Mock private lateinit var cacheDataSource: CacheDataSource
    @Mock private lateinit var localDataSource: DiskDataSource

    @Before
    fun setup() {
        //MockitoAnnotations.openMocks(this)
        sut = NewsRepositoryImpl(
            TestDefaultDispatcher(),
            remoteDataSource,
            cacheDataSource,
            localDataSource
        )
    }

    @Test
    fun `GIVEN successful remote response WHEN get news THEN returns success with domain data model`()
    = runTest {

        // given
        val data = RepositoryFactory.getSomeNews(2)

        Mockito
            .`when`(remoteDataSource.getNews("",""))
            .thenReturn(Result.success(data))

        // when
        val actual = sut.getNews("", "")

        // then
        Assert.assertTrue(actual.isSuccess)
        actual.fold(
            onSuccess = {
                Assert.assertEquals(2, it.size)
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(News::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    @Test
    fun `GIVEN empty cache and successful remote response WHEN get remote sections THEN returns success with domain data model`()
    = runTest {

        // given
        val remoteData = RepositoryFactory.getSomeSections(5)

        Mockito
            .`when`(cacheDataSource.getSections())
            .thenReturn(emptyList())

        Mockito
            .`when`(remoteDataSource.getSections())
            .thenReturn(Result.success(remoteData))

        // when
        val actual = sut.getSections()

        // then
        Assert.assertTrue(actual.isSuccess)
        actual.fold(
            onSuccess = {
                Mockito.verify(
                    cacheDataSource,
                    Mockito.times(1)
                ).saveSections(remoteData)
                Assert.assertEquals(5, it.size)
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(Section::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    @Test
    fun `GIVEN cache data WHEN get sections THEN returns success with domain data model`()
    = runTest {

        // given
        val localData = RepositoryFactory.getSomeSections(10)

        Mockito
            .`when`(cacheDataSource.getSections())
            .thenReturn(localData)

        // when
        val actual = sut.getSections()

        // then
        Assert.assertTrue(actual.isSuccess)
        actual.fold(
            onSuccess = {
                Assert.assertEquals(10, it.size)
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(Section::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    @Test
    fun `GIVEN empty cache and remote error response WHEN get sections THEN returns error with a server exception`()
    = runTest {

        // given
        Mockito
            .`when`(cacheDataSource.getSections())
            .thenReturn(emptyList())

        Mockito
            .`when`(remoteDataSource.getSections())
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Server))

        // when
        val actual = sut.getSections()

        // then
        Assert.assertTrue(actual.isFailure)
        actual.fold(
            onSuccess = { /* nothing expected */ },
            onFailure = {
                MatcherAssert.assertThat(
                    it,
                    IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
                )
            }
        )
    }
}