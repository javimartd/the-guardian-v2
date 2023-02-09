package com.javimartd.theguardian.v2.data.model

data class NewsData(
    val body: String?,
    val date: String,
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val thumbnail: String?,
    val title: String,
    val webUrl: String
)