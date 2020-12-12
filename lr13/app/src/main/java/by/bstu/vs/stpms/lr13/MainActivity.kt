package by.bstu.vs.stpms.lr13

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.bstu.vs.stpms.lr13.model.Weather
import by.bstu.vs.stpms.lr13.retrofit.RetrofitWeather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var BaseNewsUrl = "http://newsapi.org/"
    var NewsAppId = "7e0b4244a83b4402906eb588bc9932c7"
    var BaseWeatherUrl = "http://api.openweathermap.org/"
    var WeatherAppId = "94176bf6d13e644d4c512383369a13d3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val newsRetrofit = Retrofit
//            .Builder()
//            .baseUrl(BaseNewsUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val news = newsRetrofit.create(RetrofitNews::class.java)
//        news.getNews(NewsAppId).enqueue(object : Callback<News> {
//            override fun onResponse(call: Call<News>, response: Response<News>) {
//                if (response.code() == 200) {
//                    val news = response.body()
//                    news?.articles
//
//                }
//            }
//
//            override fun onFailure(call: Call<News>, t: Throwable) {
//
//            }
//        })
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        val weatherRetrofit = Retrofit
            .Builder()
            .baseUrl(BaseWeatherUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weather = weatherRetrofit.create(RetrofitWeather::class.java)
        weather.getWeatherByCityName("moscow", WeatherAppId).enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.code() == 200) {
                    val weather = response.body()
                    weather?.description

                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                val a = 6
            }
        })
    }
}