package com.javimartd.theguardian.v2.domain.news.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.Section
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke() = newsRepository.getSections()
}