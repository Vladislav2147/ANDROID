package by.bstu.vs.stpms.lr13.retrofit

import by.bstu.vs.stpms.lr13.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNews {
    @GET("/top-headlines?sources=google-news-ru&apiKey={appId}")
    fun getNews(@Query("appId") appId: String): Call<Result<List<News>>>
}