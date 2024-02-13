package com.javimartd.theguardian.v2.data.datasources.remote.news.model

import com.google.gson.annotations.SerializedName

const val STATUS_OK = "ok"

data class NewsRemoteRaw (
    @SerializedName("response")
    val newsResponse: NewsResultsRaw?
)

data class NewsResultsRaw(
    val status: String,
    val results: List<NewsRaw>?
)

data class NewsRaw (
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val fields: FieldsRaw?
)

data class FieldsRaw (
    val liveBloggingNow: String?,
    val thumbnail: String?,
    val bodyText: String?
)

data class SectionsResponseRaw (
    @SerializedName("response")
    val sectionsResponse: SectionsResultsRaw
)

data class SectionsResultsRaw(
    val status: String,
    val results: List<SectionRaw>?
)

data class SectionRaw (
    val id: String,
    val webTitle: String,
    val webUrl: String
)

