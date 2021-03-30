package com.example.drdbasemodule.pagination

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.repo.BillRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by Shaza Hassan on 3/30/21
 */
abstract class BaseDataSourceRx<Key : Any, Data : Any> : RxPagingSource<Key, Data>(),
    DataSourceFunctions<Key, Data> {

    abstract fun requestData(currentPage: Key): Single<Any>

    override fun loadSingle(params: LoadParams<Key>): Single<LoadResult<Key, Data>> {
        val position = params.key ?: currentPage

        return requestData(position)
            .subscribeOn(Schedulers.io())
            .map {
                val lastPage = getLastPage(it, position)
                val prevPage = getPrevPage(currentPage)
                val nextPage = getNextPage(position, lastPage)
                val data = parseData(it)
                toLoadResult(data, prevPage, nextPage)

            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(
        data: List<Data>,
        prevPage: Key?,
        nextPage: Key?
    ): LoadResult<Key, Data> {
        return LoadResult.Page(
            data = data,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }
}

class BillDataSource(
    private val repo: BillRepo,
    override var currentPage: Int,
    override var pageLength: Int
) : BaseDataSourceRx<Int, Bill>() {
    override fun requestData(currentPage: Int): Single<Any> {
        val offset = currentPage * pageLength
        return repo.allBills(pageLength, offset) as Single<Any>
    }

    override fun getRefreshKey(state: PagingState<Int, Bill>): Int? {
        return null
    }

    override fun getNextPage(currentPage: Int, lastPage: Int): Int? {
        if (currentPage < lastPage) {
            return currentPage + 1
        } else {
            return null
        }
    }

    override fun getPrevPage(currentPage: Int): Int? {
        if (currentPage == 0) {
            return null
        } else {
            return currentPage - 1
        }
    }

    override fun getLastPage(response: Any, currentPage: Int?): Int {
        val data = parseData(response)
        if (data.isEmpty()) {
            return 0
        } else {
            return currentPage?.plus(2) ?: 0
        }
    }

    override fun parseData(response: Any): List<Bill> {
        return response as List<Bill> ?: listOf()
    }

}