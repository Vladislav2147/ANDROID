package by.bstu.vs.stpms.lr13.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.lr13.databinding.NewsItemLayoutBinding
import by.bstu.vs.stpms.lr13.model.Article


class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
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