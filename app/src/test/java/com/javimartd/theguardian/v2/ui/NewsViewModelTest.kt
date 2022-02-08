package com.javimartd.theguardian.v2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javimartd.theguardian.v2.data.DataFactory
import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Resource
import com.javimartd.theguardian.v2.ui.mapper.newsMapToView
import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.state.NewsViewState
import junit.framework.TestCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.atomic.AtomicInteger

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock private lateinit var repository: Repository
    @Mock private lateinit var observer: Observer<NewsViewState>

    private lateinit var sut : NewsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = NewsViewModel(repository)
    }

    @Test
    fun `get content when response successful returns success resource with news and sections`()
    = runBlocking {

        // given
        val sections = mutableListOf<RawSection>()
        sections.add(RawSection("", "Music", ""))
        sections.add(RawSection("", "Life and style", ""))

        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Success(emptyList()))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Success(sections))

        // when
        sut.content.observeForever(observer)
        sut.getNewsAndSections()

        // then
        val value = sut.content.value as NewsViewState.ShowNewsAndSections
        // 1) check observer and view state
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowNewsAndSections::class.java))
        // 2) check sections
        Assert.assertEquals("Music", value.sections[0])
        Assert.assertEquals("Life and style", value.sections[1])
        // 3) check news
        Assert.assertEquals(value.news, emptyList<News>())
    }

    @Test
    fun `get content when server error (news) returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.Server))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Success(emptyList()))

        // when
        sut.content.observeForever(observer)
        sut.getNewsAndSections()

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowServerError::class.java))
    }

    @Test
    fun `get content when server error (sections) returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Success(emptyList()))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.Server))

        // when
        sut.content.observeForever(observer)
        sut.getNewsAndSections()

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowServerError::class.java))
    }

    @Test
    fun `get content when server error (both) returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews("world"))
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.Server))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.AccessDenied))

        // when
        sut.content.observeForever(observer)
        sut.getNewsAndSections()

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowServerError::class.java))

        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowGenericError::class.java))
    }

    @Test
    fun `get news when response successful returns success resource with news`()
    = runBlocking {

        val data = DataFactory.makeNews(3)

        // given
        Mockito
            .`when`(repository.getNews(""))
            .thenReturn(Resource.Success(data))

        // when
        sut.content.observeForever(observer)
        sut.getNews("")

        // then
        val value = sut.content.value as NewsViewState.ShowNews
        // 1) check observer and view state
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowNews::class.java))
        // 2) check news
        Assert.assertEquals(value.news, data.newsMapToView())
    }

    @Test
    fun `get news when unknown error returns error resource`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getNews(""))
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.Unknown))

        // when
        sut.content.observeForever(observer)
        sut.getNews("")

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowGenericError::class.java))
    }

    @Test
    fun `on section selected when response successful`()
    = runBlocking {

        // given
        val sections = mutableListOf<RawSection>()
        sections.add(RawSection("culture", "Culture", ""))
        sections.add(RawSection("games", "Games", ""))

        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Success(sections))

        Mockito
            .`when`(repository.getNews("culture"))
            .thenReturn(Resource.Success(emptyList()))

        // when
        sut.content.observeForever(observer)
        sut.onSectionSelected("Culture")

        // then
        // 1) check getSections call
        Mockito.verify(
            repository,
            Mockito.times(2)
        ).getSections()
        // 2) check get news call with correct sectionId
        Mockito.verify(
            repository,
            Mockito.times(1)
        ).getNews("culture")
        // 3) check observer and view state
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowNews::class.java))
    }

    @Test
    fun `on section selected when server error`()
    = runBlocking {

        // given
        Mockito
            .`when`(repository.getSections())
            .thenReturn(Resource.Error(ErrorTypes.RemoteErrors.Unknown))

        // when
        sut.content.observeForever(observer)
        sut.onSectionSelected("Fashion")

        // then
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(isA(NewsViewState.ShowGenericError::class.java))
    }

    @Test
    @Ignore("Pending to make it work")
    fun `when await asyncCoroutines then all terminated`() {
        val count = AtomicInteger()
        runBlocking {
            val tasks = listOf(
                async{ count.addAndGet(2000) },
                async { count.addAndGet(3000) }
            )
            tasks.awaitAll()
            Assert.assertEquals(2, count.get())
        }
    }
}