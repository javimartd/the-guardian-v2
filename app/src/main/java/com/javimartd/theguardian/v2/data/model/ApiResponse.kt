package com.javimartd.theguardian.v2.data.model

import com.google.gson.annotations.SerializedName

const val STATUS_OK = "ok"

data class NewsResponse (
    @SerializedName("response")
    val newsResponse: NewsResults
)

data class NewsResults(
    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val results: List<News>?
)

data class News (
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
    val fields: Fields?
)

data class Fields (
    @SerializedName("liveBloggingNow")
    val liveBloggingNow: String?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("bodyText")
    val bodyText: String?
)

data class SectionsResponse (
    @SerializedName("response")
    val sectionsResponse: SectionsResults
)

data class SectionsResults(
    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val results: List<Section>?
)

data class Section (
    @SerializedName("id")
    val id: String,

    @SerializedName("webTitle")
    val webTitle: String,

    @SerializedName("webUrl")
    val webUrl: String
)

