package com.example.drdbasemodule.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by shaza on 3/28/21
 */

/**
 * M -> Class of model
 * VH -> View holder
 * C -> Comparator
 */
abstract class BasePagingDataAdapter<M : Any, VH : BasePagingDataAdapter.BaseViewHolder<M>>(C: BaseComparator<M>) :
    PagingDataAdapter<M, VH>(C) {

    /**
     * you will return the layout of the item for this recycler view
     * like  return R.layout.item_view
     */
    abstract fun getItemLayout(): Int

    /**
     * Return the constructor for the ViewHolder
     * like return ViewHolder(view)
     */
    abstract fun getViewHolderConstructor(view: View): VH

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val view = LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false)
        return getViewHolderConstructor(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindView(it) }
    }

    abstract class BaseViewHolder<M>(view: View) : RecyclerView.ViewHolder(view) {
        /**
         * Set received data in the view
         */
        abstract fun bindView(item: M)
    }

    abstract class BaseComparator<M> : DiffUtil.ItemCallback<M>() {

        override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
            return compareItems(oldItem, newItem)
        }

        /**
         * every item should contain identifier and we use this identifier to check if same item of not
         * like return oldItem.id == newItem.id
         */
        abstract fun compareItems(oldItem: M, newItem: M): Boolean

        override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
            return compareContentsItems(oldItem, newItem)
        }

        /**
         * check if contains of two items are the same
         * like return oldItem == newItem
         */
        abstract fun compareContentsItems(oldItem: M, newItem: M): Boolean
    }
}
