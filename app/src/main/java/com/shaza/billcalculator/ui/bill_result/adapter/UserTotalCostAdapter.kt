package com.shaza.billcalculator.ui.bill_result.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaza.billcalculator.R
import com.shaza.billcalculator.model.User
import kotlinx.android.synthetic.main.user_item_total_cost.view.*

class UserTotalCostAdapter(private val items: List<User>) : RecyclerView.Adapter<UserTotalCostAdapter.UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_total_cost, parent, false)
        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {

        }

        fun bind(user: User) {
            itemView.username.text = user.name
            itemView.total_cost.text = user.totalCost.toString()
        }

    }
}