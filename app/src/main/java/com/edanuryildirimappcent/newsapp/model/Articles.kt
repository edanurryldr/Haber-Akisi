package com.edanuryildirimappcent.newsapp.model

import java.io.Serializable

data class Articles (
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val content: String,
        val publishedAt: String
        ): Serializable