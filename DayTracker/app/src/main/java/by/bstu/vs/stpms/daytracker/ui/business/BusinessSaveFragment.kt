package by.bstu.vs.stpms.daytracker.ui.business

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.daytracker.R
import by.bstu.vs.stpms.daytracker.databinding.FragmentBusinessSaveBinding
import by.bstu.vs.stpms.daytracker.model.entity.Business
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType
import by.bstu.vs.stpms.daytracker.view.CustomRadioButton
import java.util.*
import kotlin.collections.ArrayList


class BusinessSaveFragment : Fragment() {

    lateinit var businessViewModel: BusinessViewModel
    lateinit var business: Business

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val businessArg = BusinessSaveFragmentArgs.fromBundle(requireArguments()).Business
        businessViewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)

        businessViewModel.setLiveData(businessArg)
        business = businessViewModel.currentBusiness.value!!

        val binding: FragmentBusinessSaveBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_business_save, container, false)

        binding.apply {
            vm = businessViewModel
            fragment = this@BusinessSaveFragment
            lifecycleOwner = viewLifecycleOwner
            startButtonDate.setOnClickListener { setStartDate() }
            startButtonTime.setOnClickListener { setStartTime() }
            endButtonDate.setOnClickListener { setEndDate() }
            endButtonTime.setOnClickListener { setEndTime() }

            val radioButtons: ArrayList<CustomRadioButton> = ArrayList()
            radioButtons.add(chill)
            radioButtons.add(work)
            radioButtons.add(studying)
            radioButtons.add(road)
            radioButtons.add(lunch)
            radioButtons.add(sleep)

            radioButtons.first { business.type.id == it.id }.isChecked = true

            radioButtons.forEach { it.setOnCheckedChangeListener { button, isChecked ->
                if (isChecked) {
                    radioButtons.filter { customRadioButton -> customRadioButton.id != button.id }.forEach { customRadioButton -> customRadioButton.isChecked = false }
                    business.type = BusinessType.findById(button.id)
                    businessViewModel.setCurrent(business)
                }
            } }
        }

        return binding.root

    }

    private fun setStartDate() {
        val d = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            business.startTime.set(Calendar.YEAR, year)
            business.startTime.set(Calendar.MONTH, monthOfYear)
            business.startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            businessViewModel.setCurrent(business)
        }

        DatePickerDialog(
            requireContext(), d,
            business.startTime.get(Calendar.YEAR),
            business.startTime.get(Calendar.MONTH),
            business.startTime.get(Calendar.DAY_OF_MONTH)
        )
                .show()
    }

    private fun setEndDate() {
        val d = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            business.endTime.set(Calendar.YEAR, year)
            business.endTime.set(Calendar.MONTH, monthOfYear)
            business.endTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            businessViewModel.setCurrent(business)
        }

        DatePickerDialog(
            requireContext(), d,
            business.endTime.get(Calendar.YEAR),
            business.endTime.get(Calendar.MONTH),
            business.endTime.get(Calendar.DAY_OF_MONTH)
        )
                .show()
    }

    private fun setStartTime() {
        val t = OnTimeSetListener { _, hourOfDay, minute ->
            business.startTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            business.startTime.set(Calendar.MINUTE, minute)
            businessViewModel.setCurrent(business)
        }

        TimePickerDialog(
            requireContext(), t,
            business.startTime.get(Calendar.HOUR_OF_DAY),
            business.startTime.get(Calendar.MINUTE), true
        )
                .show()
    }

    private fun setEndTime() {
        val t = OnTimeSetListener { _, hourOfDay, minute ->
            business.endTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            business.endTime.set(Calendar.MINUTE, minute)
            businessViewModel.setCurrent(business)
        }

        TimePickerDialog(
            requireContext(), t,
            business.endTime.get(Calendar.HOUR_OF_DAY),
            business.endTime.get(Calendar.MINUTE), true
        )
                .show()
    }

    fun save() {
        businessViewModel.save(
            { e -> Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show() },
            { requireActivity().onBackPressed() }
        )
    }
}