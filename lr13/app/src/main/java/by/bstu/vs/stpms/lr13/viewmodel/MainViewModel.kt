package by.bstu.vs.stpms.lr13.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.lr13.model.News
import by.bstu.vs.stpms.lr13.model.Weather
import by.bstu.vs.stpms.lr13.retrofit.NetworkService
import by.bstu.vs.stpms.lr13.retrofit.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    var apiNews = NetworkService.newsService()
    var apiWeather = NetworkService.weatherService()

    val newsLiveData = MutableLiveData<Event<News>>()
    val weatherLiveData = MutableLiveData<Event<Weather>>()
    var city = MutableLiveData("")

    fun getNews() {
        newsLiveData.postValue(Event.loading())
        apiNews.getNews(NetworkService.NEWS_KEY).enqueue(object: Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                newsLiveData.postValue(Event.success(response.body()))
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                newsLiveData.postValue(Event.error(t))
            }
        })

    }

    fun getWeather() {
        weatherLiveData.postValue(Event.loading())
        apiWeather.getWeatherByCityName(city.value!!, NetworkService.WEATHER_KEY).enqueue(object: Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                weatherLiveData.postValue(Event.success(response.body()))
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                weatherLiveData.postValue(Event.error(t))
            }
        })

    }
}