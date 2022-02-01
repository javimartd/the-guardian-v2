package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSource
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Resource
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
class RepositoryImplTest: TestCase() {

    /**
     * https://developer.android.com/kotlin/flow/test
     */

    private lateinit var sut : Repository

    private val coroutineDispatcher = CoroutinesTestRule()

    @Mock private lateinit var remoteDataSource: RemoteDataSource
    @Mock private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = RepositoryImpl(
            coroutineDispatcher.testDispatcherProvider,
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun `get news when response successful returns success resource with news`()
    = runBlocking {

        // given
        val data = DataFactory.makeNews(2)
        Mockito
            .`when`(remoteDataSource.getNews(""))
            .thenReturn(Resource.Success(data))

        // when
        val actual = sut.getNews("")

        // then
        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Resource.Success::class.java))
        actual as Resource.Success
        Assert.assertEquals(2, actual.data.size)
        MatcherAssert.assertThat(actual.data[0], IsInstanceOf.instanceOf(RawNews::class.java))
    }

    @Test
    fun `get sections from remote when response successful returns success resource with sections`()
    = runBlocking {

        // given
        val remoteData = DataFactory.makeSections(5)
        Mockito
            .`when`(remoteDataSource.getSections())
            .thenReturn(Resource.Success(remoteData))

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(emptyList())

        // when
        val actual = sut.getSections()

        // then
        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Resource.Success::class.java))
        actual as Resource.Success
        Mockito.verify(
            localDataSource,
            Mockito.times(1)
        ).saveSections(remoteData)
        Assert.assertEquals(5, actual.data.size)
        MatcherAssert.assertThat(actual.data[0], IsInstanceOf.instanceOf(RawSection::class.java))
    }

    @Test
    fun `get sections from local returns success resource with sections`()
    = runBlocking {

        // given
        val localData = DataFactory.makeSections(10)

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(localData)

        // when
        val actual = sut.getSections()

        // then
        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Resource.Success::class.java))
        actual as Resource.Success
        Assert.assertEquals(10, actual.data.size)
        MatcherAssert.assertThat(actual.data[0], IsInstanceOf.instanceOf(RawSection::class.java))
    }

    @Test
    fun `get sections from remote when server error returns error resource with a server exception`()
    = runBlocking {

        // given
        Mockito
            .`when`(remoteDataSource.getSections())
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.Server))

        Mockito
            .`when`(localDataSource.getSections())
            .thenReturn(null)

        // when
        val actual = sut.getSections()

        // then
        MatcherAssert.assertThat(actual, IsInstanceOf.instanceOf(Resource.Error::class.java))
        (actual as Resource.Error)
        MatcherAssert.assertThat(
            actual.error,
            IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
        )
    }
}