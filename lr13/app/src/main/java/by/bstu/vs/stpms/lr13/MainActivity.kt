package by.bstu.vs.stpms.lr13

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.lr13.databinding.ActivityMainBinding
import by.bstu.vs.stpms.lr13.model.Article
import by.bstu.vs.stpms.lr13.recyclerview.ArticleAdapter
import by.bstu.vs.stpms.lr13.retrofit.event.Status
import by.bstu.vs.stpms.lr13.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.vm = mainViewModel
        binding.lifecycleOwner = this


        articleAdapter = ArticleAdapter()
        mainViewModel.newsLiveData.observe(this) { news -> articleAdapter.setArticles(news.data?.articles) }
        articleAdapter.onClickListener = object : ArticleAdapter.OnClickListener {
            override fun onVariantClick(article: Article?) {
                article?.let {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(it.link)
                    startActivity(intent)
                }
            }
        }
        recyclerView = findViewById<RecyclerView>(R.id.rv_news).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }
        val newsProgressBar: ProgressBar = findViewById(R.id.news_progress)
        val weatherProgressBar: ProgressBar = findViewById(R.id.weather_progress)

        mainViewModel.newsLiveData.observe(this, {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(this, "error " + it.t?.message, Toast.LENGTH_SHORT).show()
                    newsProgressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    newsProgressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    newsProgressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        })
        mainViewModel.getNews()

        mainViewModel.weatherLiveData.observe(this, {
            when (it.status) {
                Status.ERROR -> Toast.makeText(this, "error " + it.t?.message, Toast.LENGTH_SHORT)
                        .show()
                Status.SUCCESS -> Log.d("TAG", "weather: success")
                Status.LOADING -> Log.d("TAG", "weather: loading")
            }
        })
    }

}