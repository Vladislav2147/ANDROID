package by.bstu.vs.stpms.daytracker.ui.business

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.daytracker.databinding.BusinessItemLayoutBinding
import by.bstu.vs.stpms.daytracker.model.entity.Business
import java.util.function.Consumer


class BusinessAdapter : RecyclerView.Adapter<BusinessAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onVariantClick(business: Business)
    }

    interface OnLongClickListener {
        fun onLongVariantClick(business: Business, view: View): Boolean
    }

    private var businesses: List<Business>? = null

    val onClickListener: OnClickListener? = null
    var onLongClickListener: OnLongClickListener? = null

    fun setBusinesses(businesses: List<Business>?) {
        this.businesses = businesses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: BusinessItemLayoutBinding = BusinessItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.getRoot())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = businesses!![position]
        holder.binding?.business = business
        holder.itemView.setOnClickListener { onClickListener?.onVariantClick(business) }
        holder.itemView.setOnLongClickListener { view: View -> onLongClickListener?.onLongVariantClick(business, view) ?: false }
    }

    override fun getItemCount(): Int {
        return if (businesses == null) 0 else businesses!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: BusinessItemLayoutBinding? = DataBindingUtil.bind(v)
    }
}