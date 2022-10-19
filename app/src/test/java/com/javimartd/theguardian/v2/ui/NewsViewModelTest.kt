package com.javimartd.theguardian.v2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.ui.model.NewsUiState
import com.javimartd.theguardian.v2.ui.viewmodel.NewsViewModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock private lateinit var repository: NewsRepository
    @Mock private lateinit var observer: Observer<NewsUiState>

    private lateinit var sut : NewsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = NewsViewModel(repository)
    }

    /*@Test
    fun `get all when response successful returns success resource with news and sections`()
    = runBlocking {

        // given
        val sections = mutableListOf<SectionRaw>()
        sections.add(SectionRaw("", "Music", ""))
        sections.add(SectionRaw("", "Life and style", ""))

        Mockito
            .`when`(repository.getNews())
            .thenReturn(Result.Success(emptyList()))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Result.Success(sections))

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
    fun `get all when server error (sections) returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews())
            .thenReturn(Result.Success(emptyList()))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Result.Error(ErrorTypes.RemoteErrors.Server))

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
    fun `get all when server error (both) returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews())
            .thenReturn(Result.Error(ErrorTypes.RemoteErrors.Server))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Result.Error(ErrorTypes.RemoteErrors.AccessDenied))

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
    fun `get news when response successful returns success resource with news`()
    = runBlocking {

        val data = DataFactory.makeNews(3)

        // given
        Mockito
            .`when`(repository.getNews(""))
            .thenReturn(Result.Success(data))

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
        Assert.assertEquals(value.news, data.newsMapToView())
    }

    @Test
    fun `get news when unknown error returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews(""))
            .thenReturn(Result.Error(ErrorTypes.RemoteErrors.Unknown))

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