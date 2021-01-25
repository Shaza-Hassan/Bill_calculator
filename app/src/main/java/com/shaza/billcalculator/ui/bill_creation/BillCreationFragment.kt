package com.shaza.billcalculator.ui.bill_creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.R
import com.shaza.billcalculator.ui.MainActivity
import kotlinx.android.synthetic.main.bill_creation_fragment.*

class BillCreationFragment : Fragment() {

    companion object {
        fun newInstance() = BillCreationFragment()
    }

    private val viewModel: BillCreationFragmentViewModel by viewModels {
        BillCreationViewModelFactory((activity?.application as BillApplication))
    }

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
        createBillButton.setOnClickListener {
            val number = no_of_people_edittext.text.toString().toIntOrNull()
            if (number != null && number > 0) {
                val addTaxes = taxes_checkbox.isChecked
                val addServices = services_checkbox.isChecked
                val addDelivery = delivery_checkbox.isChecked

                val bundle = bundleOf(
                    "services" to addServices,
                    "taxes" to addTaxes,
                    "delivery" to addDelivery,
                    "numberOfPeople" to number
                )
                findNavController().navigate(R.id.action_calculatorFragment_to_billCalculatorFragment, bundle)
            }

        }

    }

}