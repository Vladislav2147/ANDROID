package by.bstu.vs.stpms.daytracker.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.daytracker.R
import by.bstu.vs.stpms.daytracker.ui.business.BusinessViewModel

class StatisticsFragment : Fragment() {

    private lateinit var businessViewModel: BusinessViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        businessViewModel =
                ViewModelProvider(this).get(BusinessViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stats, container, false)




        return root
    }
}