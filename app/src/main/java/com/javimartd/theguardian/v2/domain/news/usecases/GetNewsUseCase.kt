package com.javimartd.theguardian.v2.domain.news.usecases

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.News
import javax.inject.Inject

open class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    companion object {
        const val SHOW_FIELDS_LEVEL = "all"
        const val WORLD_NEWS_SECTION_ID = "world"
    }

    suspend operator fun invoke(sectionName: String): Result<List<News>> {

        val sectionId = if (sectionName.isEmpty()) {
            WORLD_NEWS_SECTION_ID
        } else {
            val sections = newsRepository.getSections()
            sections.fold(
                onSuccess = { data -> data.first { sectionName == it.webTitle }.id },
                onFailure = { WORLD_NEWS_SECTION_ID }
            )
        }

        return newsRepository.getNews(
            showFieldsAll = SHOW_FIELDS_LEVEL,
            sectionId = sectionId
        )
    }
}