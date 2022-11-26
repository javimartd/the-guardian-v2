package com.javimartd.theguardian.v2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.domain.usecases.GetNewsUseCase
import com.javimartd.theguardian.v2.domain.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.factory.DomainFactory
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest : TestCase() {

    /*@get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock private lateinit var getNewsUseCase: GetNewsUseCase
    @Mock private lateinit var getSectionsUseCase: GetSectionsUseCase
    @Mock private lateinit var observer: Observer<NewsUiState>

    private lateinit var sut : NewsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = NewsViewModel(getNewsUseCase, getSectionsUseCase)
    }

    @Test
    fun `get all when response successful returns success result with news and sections`()
    = runBlocking {

        // given
        val sections = mutableListOf<SectionEntity>()
        sections.add(SectionEntity("", "Music", ""))
        sections.add(SectionEntity("", "Life and style", ""))

        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.success(emptyList()))

        Mockito
            .`when`(getSectionsUseCase())
            .thenReturn(Result.success(sections))

        // when
        sut.uiState.observeForever(observer)
        sut.getAll()

        // then

        // 1) check observer and view state
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsUiState.ShowSections::class.java))

        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsUiState.ShowNews::class.java))

        // 2) check sections
        val sectionsValue = sut.uiState.value as NewsUiState.ShowSections
        Assert.assertEquals("Music", sectionsValue.sections[0])
        Assert.assertEquals("Life and style", sectionsValue.sections[1])
    }

    @Test
    fun `get all when server error (sections) returns error result`()
    = runBlocking {

        // given
        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.success(emptyList()))

        Mockito
            .`when`(getSectionsUseCase())
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Server))

        // when
        sut.uiState.observeForever(observer)
        sut.getAll()

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsUiState.ShowError::class.java))

        // 2) check error
        val value = sut.uiState.value as NewsUiState.ShowError
        Assert.assertEquals(value.message, R.string.server_error_message)
    }

    @Test
    fun `get all when server error (both) returns error result`()
    = runBlocking {

        // given
        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Server))

        Mockito
            .`when`(getSectionsUseCase())
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.AccessDenied))

        // when
        sut.uiState.observeForever(observer)
        sut.getAll()

        // then
        Mockito.verify(
            observer,
            Mockito.times(2)
        ).onChanged(isA(NewsUiState.ShowError::class.java))
    }

    @Test
    fun `get news when response successful returns success result with news`()
    = runBlocking {

        val data = DomainFactory.getSomeNews(3)

        // given
        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.success(data))

        // when
        sut.uiState.observeForever(observer)
        sut.getNews("")

        // then
        val value = sut.uiState.value as NewsUiState.ShowNews
        // 1) check observer and view state
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsUiState.ShowNews::class.java))
        // 2) check news
        Assert.assertEquals(value.news, data.map { it.com.javimartd.theguardian.v2.ui.mapper.toPresentation() })
    }

    @Test
    fun `get news when unknown error returns error result`()
    = runBlocking {

        // given
        Mockito
            .`when`(getNewsUseCase(""))
            .thenReturn(Result.failure(ErrorTypes.RemoteErrors.Unknown))

        // when
        sut.uiState.observeForever(observer)
        sut.getNews("")

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsUiState.ShowError::class.java))

        // 2) check error
        val value = sut.uiState.value as NewsUiState.ShowError
        Assert.assertEquals(value.message, R.string.generic_error_message)
    }*/
}