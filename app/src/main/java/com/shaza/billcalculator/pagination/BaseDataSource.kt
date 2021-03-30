package com.example.drdbasemodule.pagination

import androidx.paging.PagingSource

/**
 * Created by Shaza Hassan on 3/29/21
 */
abstract class BaseDataSource<Key : Any, Data : Any> : PagingSource<Key, Data>(),
    DataSourceFunctions<Key, Data> {

    abstract suspend fun requestData(currentPage: Key): Any

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Data> {
        return try {

            val currentPage = params.key ?: currentPage
            val response = requestData(currentPage)
            val data = parseData(response)
            val lastPage = getLastPage(response, currentPage)
            val prevKey = getPrevPage(currentPage)
            val nextKey = getNextPage(currentPage, lastPage)
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}