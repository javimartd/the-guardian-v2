package com.javimartd.theguardian.v2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.Resource
import com.javimartd.theguardian.v2.ui.state.NewsViewState
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock private lateinit var repository: Repository
    @Mock private lateinit var observer: Observer<NewsViewState>

    private lateinit var sut : NewsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sut = NewsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetch content returns correct data type`() = runBlocking {
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Success(emptyList()))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Success(emptyList()))

        sut.content.observeForever(observer)
        sut.fetchContent()

        val data = NewsViewState.LoadData(emptyList(), emptyList())
        Mockito.verify(observer, times(1)).onChanged(isA(data::class.java))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetch content returns an error`() = runBlocking {
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Success(emptyList()))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Error(Exception()))

        sut.content.observeForever(observer)
        sut.fetchContent()

        val data = NewsViewState.Error
        Mockito.verify(observer, times(2)).onChanged(isA(data::class.java))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetch news returns correct data type`() = runBlocking {
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Success(emptyList<RawNews>()))

        sut.content.observeForever(observer)
        sut.fetchNews("world")

        val data = NewsViewState.ShowNews(emptyList())
        Mockito.verify(observer, times(1)).onChanged(isA(data::class.java))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetch news returns an error`() = runBlocking {
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Error(Exception()))

        sut.content.observeForever(observer)
        sut.fetchNews("world")

        val data = NewsViewState.Error
        Mockito.verify(observer, times(2)).onChanged(isA(data::class.java))
    }

    @ExperimentalCoroutinesApi
    @Test
    @Ignore
    fun `select news section returns correct data type`() = runBlocking {
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Success(emptyList()))

        val l = mutableListOf<RawSection>()
        l.add(RawSection("Books", "Books", ""))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Success(emptyList()))

        sut.content.observeForever(observer)
        sut.onSelectedSection("Books")

        val data = NewsViewState.ShowNews(emptyList())
        Mockito.verify(observer).onChanged(isA(data::class.java))
    }
}