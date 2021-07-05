package com.javimartd.theguardian.v2.ui.model

data class News(val id: String,
                val sectionId: String,
                val sectionName: String,
                val title: String,
                val date: String,
                val webUrl: String,
                val thumbnail: String,
                val description: String)