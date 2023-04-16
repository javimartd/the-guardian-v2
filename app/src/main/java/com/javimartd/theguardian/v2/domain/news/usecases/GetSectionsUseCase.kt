package com.javimartd.theguardian.v2.domain.news.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.Section
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(): Result<List<Section>> {
        return newsRepository.getSections()
    }
}