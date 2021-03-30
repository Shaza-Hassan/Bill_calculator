package com.shaza.billcalculator.ui.all_bills.adapter

import android.view.View
import com.example.drdbasemodule.pagination.BasePagingDataAdapter
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.Bill
import kotlinx.android.synthetic.main.bill_item.view.*

class BillAdapter(private val itemClickListener: (Bill) -> Unit) :
    BasePagingDataAdapter<Bill, BillAdapter.BillViewHolder>(BillComparator) {


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false)
//        return BillViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
//        holder.bind(bills[position])
//        holder.itemView.setOnClickListener { itemClickListener(bills[position]) }
//    }
//
//    override fun getItemCount(): Int {
//        return bills.size
//    }

    inner class BillViewHolder(itemView: View) : BaseViewHolder<Bill>(itemView) {
        init {
            itemView.setOnClickListener { itemClickListener }
        }

        override fun bindView(item: Bill) {
            itemView.bill_name_input_layout.text = item.billName
        }

//        fun bind(bill: Bill) {
//            itemView.bill_name_input_layout.text = bill.billName
//        }
    }

    override fun getItemLayout(): Int {
        return R.layout.bill_item
    }

    override fun getViewHolderConstructor(view: View): BillViewHolder {
        return BillViewHolder(view)
    }

    object BillComparator : BaseComparator<Bill>() {
        override fun compareItems(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem.billId == newItem.billId
        }

        override fun compareContentsItems(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem == newItem
        }

    }

}