package by.bstu.vs.stpms.lr13.retrofit

import by.bstu.vs.stpms.lr13.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNews {
    @GET("/v2/top-headlines?sources=google-news-ru")
    fun getNews(@Query("apiKey") apiKey: String): Call<News>
}