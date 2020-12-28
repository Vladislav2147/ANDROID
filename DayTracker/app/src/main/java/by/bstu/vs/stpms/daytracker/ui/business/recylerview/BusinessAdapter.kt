package by.bstu.vs.stpms.daytracker.ui.business.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.daytracker.databinding.BusinessItemLayoutBinding
import by.bstu.vs.stpms.daytracker.model.entity.Business


class BusinessAdapter : RecyclerView.Adapter<BusinessAdapter.ViewHolder>() {

    private var businesses: List<Business>? = null

    var onClickListener: ((Business) -> Unit)? = null
    var onLongClickListener:((Business, View) -> Boolean)? = null

    fun setBusinesses(businesses: List<Business>?) {
        this.businesses = businesses
        notifyDataSetChanged()
    }
    fun getBusinesses() = businesses;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: BusinessItemLayoutBinding = BusinessItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.getRoot())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = businesses!![position]
        holder.binding?.business = business
        holder.itemView.setOnClickListener { onClickListener?.invoke(business) }
        holder.itemView.setOnLongClickListener { view: View -> onLongClickListener?.invoke(business, view) ?: false }
    }

    override fun getItemCount(): Int {
        return if (businesses == null) 0 else businesses!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: BusinessItemLayoutBinding? = DataBindingUtil.bind(v)
    }
}