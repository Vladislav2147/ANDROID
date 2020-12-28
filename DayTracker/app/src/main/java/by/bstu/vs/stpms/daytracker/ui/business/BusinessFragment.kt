package by.bstu.vs.stpms.daytracker.ui.business

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
                        R.id.delete_item -> deleteBusiness(business)
                    }
                    true
                }
                popupMenu.show()
                return true
            }
        }

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

    fun deleteBusiness(business: Business) {
        val builder = AlertDialog.Builder(requireContext())
        builder
                .setTitle("Delete")
                .setMessage("Delete item?")
                .setPositiveButton("Ok") { dialogInterface: DialogInterface?, i: Int -> businessViewModel.delete(business) }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}