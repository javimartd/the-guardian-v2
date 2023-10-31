package com.javimartd.theguardian.v2.features.news

import com.google.common.truth.Truth
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.domain.news.usecases.GetNewsUseCase
import com.javimartd.theguardian.v2.domain.news.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.factory.DomainFactory
import com.javimartd.theguardian.v2.features.news.mapper.toPresentation
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import com.javimartd.theguardian.v2.utils.TestDispatcherRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest : TestCase() {

    /**
     * we’re in the test environment and the Main dispatcher that wraps the Android UI thread
     * will be unavailable, as these tests are executed on a local JVM and not an Android device.
     * So we need to set our Dispatcher to Test Dispatcher before launching the coroutine.
     */
    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock private lateinit var getNewsUseCase: GetNewsUseCase
    @Mock private lateinit var getSectionsUseCase: GetSectionsUseCase

    private lateinit var sut : NewsViewModel


    @Before
    fun setup() {
        //Dispatchers.setMain(UnconfinedTestDispatcher())
        MockitoAnnotations.initMocks(this)
        sut = NewsViewModel(getNewsUseCase, getSectionsUseCase)
    }

    @After
    fun cleanup() {
        //Dispatchers.resetMain()
    }

    @Test
    fun `get all when response successful returns success result with news and sections`()
    = runTest {

        // given
        val news = DomainFactory.getSomeNews(3)

        val sections = mutableListOf<Section>()
        sections.add(Section("", "Music", ""))
        sections.add(Section("", "Life and style", ""))

        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.success(news))

        Mockito
            .`when`(getSectionsUseCase())
            .thenReturn(Result.success(sections))

        // when
        sut.getAll()

        // then
        Truth.assertThat(sut.uiState.isRefreshing).isFalse()
        Truth.assertThat(sut.uiState.sectionSelected).isEqualTo("Choose a section of interest")
        Truth.assertThat(sut.uiState.news).isNotEmpty()
        Truth.assertThat(sut.uiState.sections).isNotNull()
        Truth.assertThat(sut.uiState.errorMessage).isNull()
        Truth.assertThat(sut.uiState.sections[0]).isEqualTo("Music")
        Truth.assertThat(sut.uiState.sections[1]).isEqualTo("Life and style")
    }

    @Test
    fun `get all when server error (sections) returns error result`()
    = runTest {

        val data = DomainFactory.getSomeNews(3)

        // given
        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.success(data))

        Mockito
            .`when`(getSectionsUseCase())
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Server))

        // when
        sut.getAll()

        // then
        Truth.assertThat(sut.uiState.isRefreshing).isFalse()
        Truth.assertThat(sut.uiState.sectionSelected).isEqualTo("Choose a section of interest")
        Truth.assertThat(sut.uiState.news).contains(data[0].toPresentation())
        Truth.assertThat(sut.uiState.sections).isEmpty()
        Truth.assertThat(sut.uiState.errorMessage).isNotNull()
        Assert.assertEquals(sut.uiState.errorMessage, R.string.server_error_message)
    }

    @Test
    fun `get all when server error (both) returns error result`()
    = runTest {

        // given
        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Server))

        Mockito
            .`when`(getSectionsUseCase())
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.AccessDenied))

        // when
        sut.getAll()

        // then
        Truth.assertThat(sut.uiState.isRefreshing).isFalse()
        Truth.assertThat(sut.uiState.sectionSelected).isEqualTo("Choose a section of interest")
        Truth.assertThat(sut.uiState.news).isEmpty()
        Truth.assertThat(sut.uiState.sections).isEmpty()
        Truth.assertThat(sut.uiState.errorMessage).isNotNull()
    }

    @Test
    fun `get news when response successful returns success result with news`()
    = runTest {

        // given
        val news = DomainFactory.getSomeNews(3)

        Mockito
            .`when`(getNewsUseCase("Books"))
            .thenReturn(Result.success(news))

        // when
        sut.getNews("Books")

        // then
        Truth.assertThat(sut.uiState.isRefreshing).isFalse()
        Assert.assertEquals(sut.uiState.sectionSelected,"Books")
        Truth.assertThat(sut.uiState.news).contains(news[0].toPresentation())
    }

    @Test
    fun `get news when unknown error returns error result`()
    = runTest {

        // given
        Mockito
            .`when`(getNewsUseCase("Economy"))
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Unknown))

        // when
        sut.getNews("Economy")

        // then
        Truth.assertThat(sut.uiState.isRefreshing).isFalse()
        Assert.assertEquals(sut.uiState.errorMessage, R.string.generic_error_message)
    }
}