package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.DomainFactory
import com.javimartd.theguardian.v2.ui.CoroutinesTestRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryImplTest: TestCase() {

    private lateinit var sut : NewsRepository

    private val coroutineDispatcher = CoroutinesTestRule()

    @Mock private lateinit var remoteDataSource: NewsRemoteDataSource
    @Mock private lateinit var cacheDataSource: NewsCacheDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = NewsRepositoryImpl(
            coroutineDispatcher.testDispatcherProvider,
            remoteDataSource,
            cacheDataSource
        )
    }

    @Test
    fun `get news when response successful returns success result with news`()
    = runBlocking {

        // given
        val data = DomainFactory.getSomeNews(2)

        Mockito
            .`when`(remoteDataSource.getNews("",""))
            .thenReturn(Result.success(data))

        // when
        val actual = sut.getNews("", "")

        // then
        assertTrue(actual.isSuccess)
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
    = runBlocking {

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
        assertTrue(actual.isSuccess)
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
    = runBlocking {

        // given
        val localData = DomainFactory.getSomeSections(10)

        Mockito
            .`when`(cacheDataSource.getSections())
            .thenReturn(localData)

        // when
        val actual = sut.getSections()

        // then
        assertTrue(actual.isSuccess)
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
    = runBlocking {

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
        assertTrue(actual.isFailure)
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