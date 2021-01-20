package com.shaza.billcalculator.ui.bill_calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.Item
import com.shaza.billcalculator.model.User
import kotlinx.android.synthetic.main.user_list_item.view.*

class UserAdapter(private val items: List<User>) : RecyclerView.Adapter<UserAdapter.UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        var listOfItems = mutableListOf<Item>()/

        init {
            val linearLayoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            itemView.list_item.layoutManager = linearLayoutManager

            itemView.add_new_item.setOnClickListener {
                val item = Item()
                items[adapterPosition].listOfItems.add(item)
                notifyItemChanged(adapterPosition)
            }

            itemView.remove_item.setOnClickListener {
                val lastIndex = items[adapterPosition].listOfItems.size - 1
                items[adapterPosition].listOfItems.removeAt(lastIndex)
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(user: User) {
            itemView.username_edittext.setText(user.name)

            itemView.username_edittext_layout?.editText?.doOnTextChanged { text, _, _, _ ->
                user.name = text.toString()
            }
            val adapter = ItemAdapter(user.listOfItems)

            itemView.list_item.adapter = adapter

            if (user.listOfItems.isNotEmpty()) {
                itemView.remove_item.visibility = VISIBLE
            } else {
                itemView.remove_item.visibility = GONE
            }

            if (adapterPosition == items.size - 1) {
                itemView.divider.visibility = GONE
            } else {
                itemView.divider.visibility = VISIBLE
            }
        }

    }
}