package com.shaza.billcalculator.ui.all_bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.ui.all_bills.adapter.BillAdapter
import kotlinx.android.synthetic.main.list_of_bills_fragment.*

class ListOfBillsFragment : Fragment() {

    companion object {
        fun newInstance() = ListOfBillsFragment()
    }


    private val viewModel: ListOfBillsFragmentViewModel by viewModels {
        ListOfBillsViewModelFactory((activity?.application as BillApplication))
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: BillAdapter
    private var billList = mutableListOf<Bill>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_bills_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initAdapter()
        viewModel.getAllBills()
    }

    private fun initObserver() {
        viewModel.allBills.observe(viewLifecycleOwner, Observer {
            billList = it as MutableList<Bill>
            initAdapter()
        })
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        all_bills.layoutManager = linearLayoutManager
        adapter = BillAdapter(billList)
        all_bills.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.onDetach()
    }
}