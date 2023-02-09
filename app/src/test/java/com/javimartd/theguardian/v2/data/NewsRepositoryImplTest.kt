package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.datasources.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.repository.NewsRepositoryImpl
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.DomainFactory
import com.javimartd.theguardian.v2.utils.TestDefaultDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

    @Mock private lateinit var remoteDataSource: NewsRemoteDataSource
    @Mock private lateinit var cacheDataSource: NewsCacheDataSource
    @Mock private lateinit var localDataSource: NewsLocalDataSource

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
    fun `get news when response successful returns success result with news`()
    = runTest {

        // given
        val data = DomainFactory.getSomeNews(2)

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
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(NewsEntity::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    @Test
    fun `get remote sections when response successful returns success result with sections`()
    = runTest {

        // given
        val remoteData = DomainFactory.getSomeSections(5)

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
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(SectionEntity::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    @Test
    fun `get cached sections returns success result with sections`()
    = runTest {

        // given
        val localData = DomainFactory.getSomeSections(10)

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
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(SectionEntity::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    @Test
    fun `get remote sections when server error exception returns error result`()
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