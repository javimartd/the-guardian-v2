package com.javimartd.theguardian.v2.features.news

import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.domain.news.usecases.GetNewsUseCase
import com.javimartd.theguardian.v2.domain.news.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.spy
import org.mockito.kotlin.times


/**
https://developer.android.com/kotlin/flow
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    /**
     we’re in the test environment and the Main dispatcher that wraps the Android UI thread
     will be unavailable, as these tests are executed on a local JVM and not an Android device.
     So we need to set our Dispatcher to Test Dispatcher before launching the coroutine.
     */
    /*@get: Rule
    val dispatcherRule = TestDispatcherRule()*/

    @Mock private lateinit var getNewsUseCase: GetNewsUseCase
    @Mock private lateinit var getSectionsUseCase: GetSectionsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    /*@Test
    fun `searchResults should emit empty list on error`() = runTest {
        // Arrange
        Mockito
            .`when`(getNewsUseCase.invoke(""))
            .thenReturn(Result.success(emptyList()))

        Mockito
            .`when`(getSectionsUseCase.invoke())
            .thenReturn(flowOf(emptyList()))

        val sut = NewsViewModel(getNewsUseCase, getSectionsUseCase)

        // Act
        val result = mutableListOf<SectionData>()
        sut.searchResults.collect {
            //result.add(it)
            // Assert
            Assert.assertEquals(emptyList<SectionData>(), it)
        }
    }*/

    @Test
    fun `onIntent SearchQueryClick call getNews function`() {
        // Arrange
        val sut = NewsViewModel(getNewsUseCase, getSectionsUseCase)
        val sectionId = ""
        val intent = NewsUiContract.Intent.SearchQueryClick(sectionId)

        // Act
        val spyViewModel = spy(sut)
        spyViewModel.onIntent(intent)

        // Assert
        Mockito.verify(spyViewModel, times(1)).getNews(sectionId)
    }

    @Test
    fun `getNews when successful response with data should return News State`() {

    }

    @Test
    fun `getNews when successful response with No data should return NoNews State`() {

    }

    @Test
    fun `getNews when error response should return Error State`() {

    }

    @Test
    fun `handleError should set correct error message for Network error`() {
        // Arrange
        val sut = NewsViewModel(getNewsUseCase, getSectionsUseCase)

        // Act
        sut.handleError(ErrorTypes.RemoteErrors.Network)

        // Assert
        val uiState = sut.uiState
        Assert.assertEquals(NewsUiContract.State.Error(R.string.network_error_message), uiState)
    }

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/
}