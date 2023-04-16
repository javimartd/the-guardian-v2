package com.javimartd.theguardian.v2.domain.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.domain.news.usecases.GetNewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetNewsUseCaseTest {

    private lateinit var sut : GetNewsUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = GetNewsUseCase(newsRepository)
    }

    @Test
    fun `get news when user opens the app`()
    = runTest {

            // when
            sut.invoke("")

            // then
            Mockito.verify(
                newsRepository,
                times(1)
            ).getNews("all", "world")
        }

    @Test
    fun `get news when user selects a section from the dropdown menu`()
    = runTest {

            // given
            val sections = listOf(
                Section(id = "books", webTitle = "Books", webUrl = ""),
                Section(id = "education", webTitle = "Education", webUrl = "")
            )

            Mockito
                .`when`(newsRepository.getSections())
                .thenReturn(Result.success(sections))

            // when
            sut.invoke("Education")

            // then
            Mockito.verify(
                newsRepository, times(1)
            ).getNews("all", "education")
        }
}