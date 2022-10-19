package com.javimartd.theguardian.v2.domain.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(): Result<List<SectionEntity>> {
        return newsRepository.getSections()
    }
}