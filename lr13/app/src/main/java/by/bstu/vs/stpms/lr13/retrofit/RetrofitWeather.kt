package by.bstu.vs.stpms.lr13.retrofit

import by.bstu.vs.stpms.lr13.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitWeather {
    @GET("weather?q={city}&appid={appId}&units=metric")
    fun getWeatherByCityName(@Query("city") city: String, @Query("appId") appId: String): Call<Result<Weather>>
}