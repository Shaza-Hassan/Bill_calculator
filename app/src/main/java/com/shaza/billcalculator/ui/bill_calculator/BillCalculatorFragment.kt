package com.shaza.billcalculator.ui.bill_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.User
import com.shaza.billcalculator.ui.MainActivity
import com.shaza.billcalculator.ui.bill_calculator.adapter.UserAdapter
import kotlinx.android.synthetic.main.bill_calculator_fragment.*

class BillCalculatorFragment : Fragment() {

    companion object {
        fun newInstance() = BillCalculatorFragment()
    }

    private val viewModel: BillCalculatorFragmentViewModel by viewModels {
        BillCalculatorViewModelFactory((activity?.application as BillApplication))
    }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: UserAdapter

    private var userList = mutableListOf<User>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_calculator_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData(arguments)
        initObserver()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.title = context?.getString(R.string.app_name)
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

        viewModel.addedToDB.observe(viewLifecycleOwner, Observer {
            if (it) {
                val bundle = Bundle()
                bundle.putParcelableArray("users", viewModel.userListLiveData.value!!.toTypedArray())
                findNavController().navigate(R.id.action_billCalculatorFragment_to_billReusltFragment, bundle)
            }
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
            viewModel.createBillInDBAndCalculate()
        }
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        persons_in_bill.layoutManager = linearLayoutManager
        adapter = UserAdapter(userList)
        persons_in_bill.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.onDetach()
    }
}