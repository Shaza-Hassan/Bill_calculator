package com.shaza.billcalculator.ui.bill_calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.Item
import kotlinx.android.synthetic.main.item_list.view.*

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Item) {
            if (item.itemPrice.toString().isNotEmpty()) {
                itemView.item_price_edit_text.setText(item.itemPrice.toString())
            }
            itemView.item_name_edit_text.setText(item.itemName)

            itemView.item_price_layout?.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    item.itemPrice = text.toString().toDouble()
                }
            }

            itemView.item_name_layout?.editText?.doOnTextChanged { text, _, _, _ ->
                item.itemName = text.toString()
            }
        }

    }
}