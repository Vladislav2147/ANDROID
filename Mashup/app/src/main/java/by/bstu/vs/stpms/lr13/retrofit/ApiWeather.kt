package by.bstu.vs.stpms.lr13.retrofit

import by.bstu.vs.stpms.lr13.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeather {
    @GET("data/2.5/weather?units=metric")
    fun getWeatherByCityName(@Query("q") city: String, @Query("appId") appId: String): Call<Weather>
}