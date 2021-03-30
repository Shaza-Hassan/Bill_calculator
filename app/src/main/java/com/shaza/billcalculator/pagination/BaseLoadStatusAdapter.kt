package com.example.drdbasemodule.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Shaza Hassan on 3/28/21
 */

/**
 * VH -> View holder
 */
abstract class BaseLoadStatusAdapter<VH : BaseLoadStatusAdapter.BaseLoadStateViewHolder> :
    LoadStateAdapter<VH>() {

    /**
     * you will return the layout of the item for the footer or header for the recycler
     * that show the loader while get new data
     * or error message if can't get data
     * like  return R.layout.item_view
     */
    abstract fun getItemLayout(): Int

    /**
     * Return the constructor for the ViewHolder
     * like return ViewHolder(view)
     */
    abstract fun getViewHolderConstructor(view: View): VH

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VH {
        val view = LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false)
        return getViewHolderConstructor(view)
    }

    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
        holder.bindView(loadState)
    }

    abstract class BaseLoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(loadState: LoadState)
    }
}
