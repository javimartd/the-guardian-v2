package com.javimartd.theguardian.v2.data.datasources.remote

import com.google.gson.annotations.SerializedName

const val STATUS_OK = "ok"

data class NewsResponseRaw (
    @SerializedName("response")
    val newsResponse: NewsResultsRaw?
)

data class NewsResultsRaw(
    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val results: List<NewsRaw>?
)

data class NewsRaw (
    @SerializedName("id")
    val id: String,

    @SerializedName("sectionId")
    val sectionId: String,

    @SerializedName("sectionName")
    val sectionName: String,

    @SerializedName("webPublicationDate")
    val webPublicationDate: String,

    @SerializedName("webTitle")
    val webTitle: String,

    @SerializedName("webUrl")
    val webUrl: String,

    @SerializedName("fields")
    val fields: FieldsRaw?
)

data class FieldsRaw (
    @SerializedName("liveBloggingNow")
    val liveBloggingNow: String?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("bodyText")
    val bodyText: String?
)

data class SectionsResponseRaw (
    @SerializedName("response")
    val sectionsResponse: SectionsResultsRaw
)

data class SectionsResultsRaw(
    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val results: List<SectionRaw>?
)

data class SectionRaw (
    @SerializedName("id")
    val id: String,

    @SerializedName("webTitle")
    val webTitle: String,

    @SerializedName("webUrl")
    val webUrl: String
)

