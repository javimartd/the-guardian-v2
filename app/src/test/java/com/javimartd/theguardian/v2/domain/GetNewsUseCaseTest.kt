package com.javimartd.theguardian.v2.domain

import com.javimartd.theguardian.v2.domain.usecases.GetNewsUseCase
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNewsUseCaseTest {

    private lateinit var sut : GetNewsUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        sut = GetNewsUseCase(newsRepository)
    }
}