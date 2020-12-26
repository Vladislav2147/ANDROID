package by.bstu.vs.stpms.daytracker.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.bstu.vs.stpms.daytracker.R
import by.bstu.vs.stpms.daytracker.databinding.BusinessItemLayoutBinding
import by.bstu.vs.stpms.daytracker.databinding.FragmentBusinessBinding
import by.bstu.vs.stpms.daytracker.model.entity.Business
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType
import java.util.*

class BusinessFragment : Fragment() {

    private lateinit var businessViewModel: BusinessViewModel

    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        businessViewModel =
                ViewModelProvider(this).get(BusinessViewModel::class.java)
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        val view = binding.root
        val adapter = BusinessAdapter()

        businessViewModel.businessesLiveData.observe(viewLifecycleOwner, {businesses -> adapter.setBusinesses(businesses)})
        binding.rvBusiness.adapter = adapter
        binding.rvBusiness.layoutManager = LinearLayoutManager(context)


        val start = Calendar.getInstance()
        val end = Calendar.getInstance()

        start.time = Date()
        start.add(Calendar.HOUR_OF_DAY, -2)
        end.time = Date()

        val start1 = Calendar.getInstance()
        val end1 = Calendar.getInstance()

        start1.time = Date()
        start1.add(Calendar.HOUR_OF_DAY, -3)
        end1.time = Date()
        end1.add(Calendar.HOUR_OF_DAY, -1)

        val business1 = Business(
            startTime = start,
            endTime = end,
            type = BusinessType.CHILL
        )

        val business2 = Business(
            startTime = start1,
            endTime = end1,
            type = BusinessType.SLEEP
        )

        businessViewModel.insert(business1)
        businessViewModel.insert(business2)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}