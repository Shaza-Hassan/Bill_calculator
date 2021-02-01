package com.shaza.billcalculator.ui.bill_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.User
import com.shaza.billcalculator.ui.MainActivity
import com.shaza.billcalculator.ui.bill_result.adapter.UserTotalCostAdapter
import kotlinx.android.synthetic.main.bill_result_fragment.*


class BillResultFragment : Fragment() {

    companion object {
        fun newInstance() = BillResultFragment()
    }

    private val viewModel: BillResultFragmentViewModel by viewModels {
        BillResultViewModelFactory((activity?.application as BillApplication))
    }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var userList = mutableListOf<User>()
    private lateinit var adapter: UserTotalCostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bill_result_fragment, container, false)
    }


    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.title = context?.getString(R.string.bill_result)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_billResultFragment_to_listOfBillsFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { viewModel.getData(it) }
        initObserver()
        initAdapter()
    }

    private fun initObserver() {
        viewModel.userListLiveData.observe(viewLifecycleOwner, Observer {
            userList = it
            initAdapter()
        })

        viewModel.billIdLiveData.observe(viewLifecycleOwner, Observer {
            if (it != -1) {
                viewModel.getAllUsersForThisBill(it)
            }
        })
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        userListWithCost.layoutManager = linearLayoutManager
        adapter = UserTotalCostAdapter(userList)
        userListWithCost.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.onDetach()
    }
}