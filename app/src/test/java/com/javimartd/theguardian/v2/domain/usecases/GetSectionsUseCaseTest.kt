package com.javimartd.theguardian.v2.domain.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.DomainFactory
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSectionsUseCaseTest {

    private lateinit var sut : GetSectionsUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        sut = GetSectionsUseCase(newsRepository)
    }

    @Test
    fun invoke() {
        runBlocking {

            // when
            sut.invoke()

            Mockito.verify(
                newsRepository,
                Mockito.times(1)
            ).getSections()
        }
    }

    @Test
    fun invoke2() {
        runBlocking {

            // given
            val remoteData = DomainFactory.getSomeSections(5)

            Mockito
                .`when`(newsRepository.getSections())
                .thenReturn(Result.success(remoteData))

            // when
            val actual = sut.invoke()

            actual.fold(
                onSuccess = {
                    MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(SectionEntity::class.java))
                },
                onFailure = {}
            )

            Mockito.verify(
                newsRepository,
                Mockito.times(1)
            ).getSections()



        }
    }
}