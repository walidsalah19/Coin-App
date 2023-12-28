package com.app.coin.Data.DataSource

import com.app.coin.Data.DataSource.DataClass.CoinDataClass.DataClass
import com.app.coin.Data.DataSource.DataClass.CoinDetailsDataClass.CoinDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinInterface {
    @GET("/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&sparkline=false")
    suspend fun getCoin(@Query("page")page:String): Response<DataClass>

    @GET("/api/v3/coins/{id}")
    suspend fun getCoinById(@Path("id") id:String): Response<CoinDetails>
}