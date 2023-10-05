package com.labwhisper.beerchallenge.service

import android.util.Log
import com.labwhisper.beerchallenge.beer.Beer
import retrofit2.Response

//FIXME Interface out at retrofit needs to be hidden
class BeerService(
    private val beerEndpoint: BeerEndpoint,
    private val beerApiResponseMapper: BeerApiResponseMapper,
) {

    suspend fun getBeerList(page: Int = 1, perPage: Int = 25): List<Beer> {
        val allBeersResponse = beerEndpoint.getAllBeers(page = page, perPage = perPage)
        if (!allBeersResponse.isSuccessful) {
            Log.e("BeerService", "getBeerList: ${allBeersResponse.errorBody()}")
            return emptyList()
        }

        return retrieveBeerList(allBeersResponse)
    }

    suspend fun getBeerById(beerId: Int): Beer? {
        val beerResponse = beerEndpoint.getBeerById(beerId)
        if (!beerResponse.isSuccessful) {
            Log.e("BeerService", "getBeerById: ${beerResponse.errorBody()}")
            return null
        }

        return beerResponse.body()?.let {
            it.firstOrNull()?.let { responseModel -> beerApiResponseMapper.map(responseModel) }
        }
    }

    private fun BeerService.retrieveBeerList(allBeersResponse: Response<List<BeerApiResponseModel>>) =
        allBeersResponse.body()?.let { beerList ->
            Log.d("BeerService", "getBeerList: ${beerList.size}")
            beerList.map(beerApiResponseMapper::map)
        } ?: run {
            Log.e("BeerService", "getBeerList body is null: ${allBeersResponse.errorBody()}")
            emptyList()
        }
}