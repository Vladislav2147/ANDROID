package by.bstu.vs.stpms.daytracker.ui.business

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.bstu.vs.stpms.daytracker.MainActivity
import by.bstu.vs.stpms.daytracker.R
import by.bstu.vs.stpms.daytracker.databinding.FragmentBusinessBinding
import by.bstu.vs.stpms.daytracker.model.entity.Business
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class BusinessFragment : Fragment() {

    private lateinit var businessViewModel: BusinessViewModel
    private lateinit var navController: NavController

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

        businessViewModel.businessesLiveData.observe(viewLifecycleOwner, { businesses ->
            adapter.setBusinesses(
                businesses
            )
        })
        binding.rvBusiness.adapter = adapter
        binding.rvBusiness.layoutManager = LinearLayoutManager(context)

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        adapter.onLongClickListener = object: BusinessAdapter.OnLongClickListener {
            override fun onLongVariantClick(business: Business, view: View): Boolean {
                val popupMenu = PopupMenu(context!!, view, Gravity.END)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                    when (menuItem.itemId) {
                        R.id.edit_item -> {
                            val action = BusinessFragmentDirections.actionNavBusinessToBusinessSaveFragment(business)
                            navController.navigate(action)
                        }
                        R.id.delete_item -> businessViewModel.delete(business)
                    }
                    true
                }
                popupMenu.show()
                return true
            }
        }

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()

        start.time = Date()
        start.add(Calendar.DAY_OF_MONTH, -1)
        end.time = Date()
//        end.add(Calendar.DAY_OF_MONTH)

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

//        val business2 = Business(
//            startTime = start1,
//            endTime = end1,
//            type = BusinessType.SLEEP
//        )

        businessViewModel.insert(
            business1,
            { e -> Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show() },
            { Toast.makeText(context, "success", Toast.LENGTH_SHORT).show() }
        )
//        businessViewModel.insert(business2)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fab = (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val action = BusinessFragmentDirections.actionNavBusinessToBusinessSaveFragment(Business())
            navController.navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}