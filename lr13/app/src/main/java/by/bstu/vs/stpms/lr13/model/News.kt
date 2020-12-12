package by.bstu.vs.stpms.lr13.model

import com.google.gson.annotations.SerializedName

class News {
    @SerializedName("articles")
    var articles: List<Article>? = null
    class Article {
        @SerializedName("title")
        var title: String? = null
        @SerializedName("url")
        var link: String? = null
    }
}