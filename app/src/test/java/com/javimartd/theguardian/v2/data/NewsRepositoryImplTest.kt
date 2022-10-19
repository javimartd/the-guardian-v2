package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.*
import com.javimartd.theguardian.v2.domain.NewsRepository
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
    @Mock private lateinit var localDataSource: NewsCacheDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = NewsRepositoryImpl(
            coroutineDispatcher.testDispatcherProvider,
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun `get news when response successful returns success result with news`()
    = runBlocking {

        // given
        val data = DataFactory.makeNews(2)

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(emptyList())

        Mockito
            .`when`(remoteDataSource.getNews(sectionId = NewsRepository.WORLD_NEWS_SECTION_ID))
            .thenReturn(Result.success(data))

        // when
        val actual = sut.getNews("")

        // then
        assertTrue(actual.isSuccess)
        actual.fold(
            onSuccess = {
                Assert.assertEquals(2, it.size)
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(NewsRaw::class.java))
            },
            onFailure = { /* nothing expected */ }
        )
    }

    /*@Test
    fun `get remote sections when response successful returns success result with sections`()
    = runBlocking {

        // given
        val remoteData = DataFactory.makeSections(5)

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(emptyList())

        Mockito
            .`when`(remoteDataSource.getSections())
            .thenReturn(Result.success(remoteData))

        // when
        val actual = sut.getSections()

        // then

        actual.fold(
            on
        )

        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Result.Success::class.java))
        actual as Result.Success
        Mockito.verify(
            localDataSource,
            Mockito.times(1)
        ).saveSections(remoteData)
        Assert.assertEquals(5, actual.data.size)
        MatcherAssert.assertThat(actual.data[0], IsInstanceOf.instanceOf(SectionRaw::class.java))
    }*/

    /*@Test
    fun `get loca sections returns success result with sections`()
    = runBlocking {

        // given
        val localData = DataFactory.makeSections(10)

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(localData)

        // when
        val actual = sut.getSections()

        // then
        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Result.Success::class.java))
        actual as Result.Success
        Assert.assertEquals(10, actual.data.size)
        MatcherAssert.assertThat(actual.data[0], IsInstanceOf.instanceOf(SectionRaw::class.java))
    }*/

    /*@Test
    fun `get remote sections when server error exception returns error result`()
    = runBlocking {

        // given

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(emptyList())

        Mockito
            .`when`(remoteDataSource.getSections())
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Server))

        // when
        val actual = sut.getSections()

        // then
        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Result.Error::class.java))
        (actual as Result.Error)
        MatcherAssert.assertThat(
            actual.error,
            IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
        )
    }*/
}