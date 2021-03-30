package com.example.drdbasemodule.pagination

/**
 * Created by Shaza Hassan on 3/30/21
 */
interface DataSourceFunctions<Key : Any, Data : Any> {

    abstract var currentPage: Key
    abstract var pageLength: Key

    abstract fun getNextPage(currentPage: Key, lastPage: Key): Key?

    abstract fun getPrevPage(currentPage: Key): Key?

    abstract fun getLastPage(response: Any, currentPage: Key? = null): Key

    abstract fun parseData(response: Any): List<Data>
}