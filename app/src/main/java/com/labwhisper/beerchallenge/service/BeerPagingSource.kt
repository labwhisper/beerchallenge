package com.labwhisper.beerchallenge.service

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.labwhisper.beerchallenge.beer.Beer
import com.labwhisper.beerchallenge.beerlist.GetBeerListUseCase
import javax.inject.Inject

class BeerPagingSource @Inject constructor(
    private val getBeerListUseCase: GetBeerListUseCase,

    ) : PagingSource<Int, Beer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        return try {
            val page = params.key ?: 1
            val beers = getBeerListUseCase.getAllBeers(page, params.loadSize)
            LoadResult.Page(
                data = beers,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (beers.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            handleError(exception)
        }
    }

    private fun handleError(throwable: Throwable): LoadResult.Error<Int, Beer> {
        Log.e("BeerPagingSource", "load: ${throwable.message}", throwable)
        return LoadResult.Error(throwable)
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition
    }
}

