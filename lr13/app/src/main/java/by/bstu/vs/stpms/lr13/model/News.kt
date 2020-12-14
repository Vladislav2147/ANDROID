package by.bstu.vs.stpms.lr13.model

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*


class News {
    @SerializedName("articles")
    var articles: List<Article>? = null

}

class Article {
    @SerializedName("title")
    var title: String? = null
    @SerializedName("url")
    var link: String? = null
    @SerializedName("publishedAt")
    var publishedAt: Date? = null

    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun bindDate(textView: TextView, date: Date?) {
            val dateFormat = SimpleDateFormat("dd.MM.YYYY HH:mm");
            textView.text = date?.let { dateFormat.format(date) }
        }
    }
}