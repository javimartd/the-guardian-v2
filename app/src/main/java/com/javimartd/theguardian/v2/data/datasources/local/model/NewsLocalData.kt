package com.javimartd.theguardian.v2.data.datasources.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsLocalData (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "newsId") var newsId: String,
    @ColumnInfo(name = "body") var body: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "sectionId") var sectionId: String,
    @ColumnInfo(name = "sectionName") var sectionName: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "thumbnail") var thumbnail: String,
    @ColumnInfo(name = "webUrl") var webUrl: String
)
