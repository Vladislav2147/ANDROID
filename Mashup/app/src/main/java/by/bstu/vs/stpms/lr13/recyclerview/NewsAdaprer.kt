package by.bstu.vs.stpms.lr13.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.databinding.NewsItemLayoutBinding
import by.bstu.vs.stpms.lr13.model.Article
import com.squareup.picasso.Picasso


class ArticleAdapter(private val context: Context) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onVariantClick(article: Article?)
    }

    var news: List<Article>? = null

    var onClickListener: OnClickListener? = null

    fun setArticles(news: List<Article>?) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: NewsItemLayoutBinding = NewsItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Article = news!![position]
        holder.binding?.article = article
        Picasso.with(context)
            .load(article.imageUrl)
            .placeholder(R.drawable.ic_baseline_no_photography_24)
            .error(R.drawable.ic_baseline_no_photography_24)
            .into(holder.itemView.findViewById<ImageView>(R.id.image))
        if (onClickListener != null) {
            holder.itemView.setOnClickListener {
                onClickListener?.onVariantClick(
                    article
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (news == null) 0 else news!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: NewsItemLayoutBinding?

        init {
            binding = DataBindingUtil.bind(v)
        }
    }
}