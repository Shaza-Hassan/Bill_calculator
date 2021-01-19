package com.shaza.billcalculator.ui.bill_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.User
import com.shaza.billcalculator.ui.bill_calculator.adapter.UserAdapter
import kotlinx.android.synthetic.main.bill_calculator_fragment.*

class BillCalculatorFragment : Fragment() {

    companion object {
        fun newInstance() = BillCalculatorFragment()
    }

    private lateinit var viewModel: BillCalculatorFragmentViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: UserAdapter
    private var userList = mutableListOf<User>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_calculator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BillCalculatorFragmentViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getData(arguments)
        initObserver()
        initListener()
    }

    private fun initObserver() {
        viewModel.addTaxes.observe(viewLifecycleOwner, Observer {
            if (it) {
                taxes_input_layout.visibility = VISIBLE
            } else {
                taxes_input_layout.visibility = GONE
            }
        })

        viewModel.addDelivery.observe(viewLifecycleOwner, Observer {
            if (it) {
                delivery_input_layout.visibility = VISIBLE
            } else {
                delivery_input_layout.visibility = GONE
            }
        })

        viewModel.addServices.observe(viewLifecycleOwner, Observer {
            if (it) {
                services_input_layout.visibility = VISIBLE
            } else {
                services_input_layout.visibility = GONE
            }
        })

        viewModel.userListLiveData.observe(viewLifecycleOwner, Observer {
            userList = it
            initAdapter()
        })
    }

    private fun initListener() {
        taxes_input_layout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateTaxesValue(text.toString())
        }

        services_input_layout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateServicesValue(text.toString())
        }

        delivery_input_layout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateDeliveryValue(text.toString())
        }

        calculate_button.setOnClickListener {
            viewModel.calculateEachOneCost()
        }
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        persons_in_bill.layoutManager = linearLayoutManager
        adapter = UserAdapter(userList)
        persons_in_bill.adapter = adapter
    }
}