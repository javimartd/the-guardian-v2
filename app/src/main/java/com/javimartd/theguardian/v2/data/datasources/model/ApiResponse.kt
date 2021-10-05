package com.javimartd.theguardian.v2.data.datasources.model

import com.google.gson.annotations.SerializedName

const val STATUS_OK = "ok"

data class NewsResponse (
    @SerializedName("response")
    val newsResponse: RawNewsResults?
)

data class RawNewsResults(
    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val results: List<RawNews>?
)

data class RawNews (
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
    val fields: RawFields?
)

data class RawFields (
    @SerializedName("liveBloggingNow")
    val liveBloggingNow: String?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("bodyText")
    val bodyText: String?
)

data class SectionsResponse (
    @SerializedName("response")
    val sectionsResponse: RawSectionsResults
)

data class RawSectionsResults(
    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val results: List<RawSection>?
)

data class RawSection (
    @SerializedName("id")
    val id: String,

    @SerializedName("webTitle")
    val webTitle: String,

    @SerializedName("webUrl")
    val webUrl: String
)

