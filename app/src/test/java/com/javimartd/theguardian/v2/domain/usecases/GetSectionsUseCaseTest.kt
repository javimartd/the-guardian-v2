package com.javimartd.theguardian.v2.domain.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.domain.news.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.factory.DomainFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetSectionsUseCaseTest {

    private lateinit var sut : GetSectionsUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = GetSectionsUseCase(newsRepository)
    }

    @Test
    fun invoke() = runTest {

        // when
        sut.invoke()

        // then
        Mockito.verify(
            newsRepository,
            Mockito.times(1)
        ).getSections()
    }

    /*@Test
    fun invoke2() = runTest {

        // given
        val remoteData = DomainFactory.getSomeSections(5)

        Mockito
            .`when`(newsRepository.getSections())
            .thenReturn(Result.success(remoteData))

        // when
        val actual = sut.invoke()

        actual.fold(
            onSuccess = {
                MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(Section::class.java))
            },
            onFailure = {}
        )

        // then
        Mockito.verify(
            newsRepository,
            Mockito.times(1)
        ).getSections()
    }*/
}