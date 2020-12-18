package by.bstu.vs.stpms.lr13.retrofit

import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson




object NetworkService {

    private const val BASE_NEWS_URL = "http://newsapi.org/"
    const val NEWS_KEY = "7e0b4244a83b4402906eb588bc9932c7"
    private const val BASE_WEATHER_URL = "http://api.openweathermap.org/"
    const val WEATHER_KEY = "94176bf6d13e644d4c512383369a13d3"


    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    private val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

    fun newsService(): ApiNews {
        return Retrofit.Builder()
                .baseUrl(BASE_NEWS_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(ApiNews::class.java)
    }

    fun weatherService(): ApiWeather {
        return Retrofit.Builder()
                .baseUrl(BASE_WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiWeather::class.java)
    }
}