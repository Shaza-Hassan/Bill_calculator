package com.shaza.billcalculator.ui.all_bills.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.Bill
import kotlinx.android.synthetic.main.bill_item.view.*

class BillAdapter(private val bills: List<Bill>) :
    RecyclerView.Adapter<BillAdapter.BillViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false)
        return BillViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.bind(bills[position])
    }

    override fun getItemCount(): Int {
        return bills.size
    }

    inner class BillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bill: Bill) {
            itemView.bill_name.text = bill.billName
        }
    }

}