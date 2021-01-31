package com.shaza.billcalculator.ui.bill_creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shaza.billcalculator.R
import com.shaza.billcalculator.ui.MainActivity
import kotlinx.android.synthetic.main.bill_creation_fragment.*

class BillCreationFragment : Fragment() {

    companion object {
        fun newInstance() = BillCreationFragment()
    }

    private lateinit var viewModel: BillCreationFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_creation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BillCreationFragmentViewModel::class.java)
        viewModel.onCreate()
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.title =
            context?.getString(R.string.bill_creation)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.onDetach()
    }

    private fun initClickListener() {
        no_of_people_layout?.editText?.doOnTextChanged { text, _, _, _ ->
            no_of_people_layout.isErrorEnabled = false
            viewModel.updateNoOfPerson(text.toString().toIntOrNull())
        }

        bill_name_input_layout?.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateBillName(text.toString())
        }

        taxes_checkbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateTaxes(isChecked)
        }

        services_checkbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateServices(isChecked)
        }

        delivery_checkbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateDelivery(isChecked)
        }
        createBillButton.setOnClickListener {
            checkFields()
        }

    }

    private fun checkFields() {

        if (viewModel.checkEmptyOfNoOfPerson()) {
            no_of_people_layout.isErrorEnabled = true
            no_of_people_layout.error = getString(R.string.no_of_is_empty)
        } else if (!viewModel.checkRightValueOfNoOfPerson()) {
            no_of_people_layout.isErrorEnabled = true
            no_of_people_layout.error = getString(R.string.no_of_person_greater_than_zero)
        } else {
            val name = viewModel.billNameLD.value ?: "Bill name"
            val number = viewModel.noOfPersonsOnBillLD.value
            val addTaxes = viewModel.taxesLD.value
            val addServices = viewModel.servicesLD.value
            val addDelivery = viewModel.deliveryLD.value

            val bundle = bundleOf(
                    "services" to addServices,
                    "taxes" to addTaxes,
                    "delivery" to addDelivery,
                    "numberOfPeople" to number,
                    "name" to name
            )
            findNavController().navigate(R.id.action_calculatorFragment_to_billCalculatorFragment, bundle)
        }
    }
}