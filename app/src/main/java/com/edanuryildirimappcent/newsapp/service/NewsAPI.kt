package com.edanuryildirimappcent.newsapp.service

import com.edanuryildirimappcent.newsapp.model.NewsModel
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsAPI {

    @GET("everything?q=keyword&apiKey=311896afc7f843c181a296b6c53677c4")
    fun getData(): Observable<NewsModel>

}