package com.shaza.billcalculator.ui.all_bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.ui.MainActivity
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
        initListener()
        viewModel.getAllBills()
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.title = context?.getString(R.string.all_bills)
    }

    private fun initObserver() {
        viewModel.allBills.observe(viewLifecycleOwner, Observer {
            billList = it as MutableList<Bill>
            if (billList.isEmpty()) {
                all_bills.visibility = GONE
                no_bills.visibility = VISIBLE
            } else {
                all_bills.visibility = VISIBLE
                no_bills.visibility = GONE
                initAdapter()
            }
        })

        viewModel.uiErrorLiveData.observe(viewLifecycleOwner, Observer {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1).create() }
            with(builder) {
                this?.setTitle(getString(R.string.error_title))
                this?.setMessage(it)
                this?.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
            }
        })
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        all_bills.layoutManager = linearLayoutManager
        adapter = BillAdapter(billList) {
            val bundle = bundleOf(
                    "billId" to it.billId?.toInt()
            )
            findNavController().navigate(R.id.action_listOfBillsFragment_to_billResultFragment, bundle)
        }
        all_bills.adapter = adapter
    }

    private fun initListener() {
        add_new_bill.setOnClickListener {
            findNavController().navigate(R.id.action_listOfBillsFragment_to_billCreationFragment)
        }
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