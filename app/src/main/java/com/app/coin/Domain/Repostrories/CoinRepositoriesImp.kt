package com.app.coin.Domain.Repostrories

import com.app.coin.Data.DataSource.DataClass.CoinDataClass.DataClass
import com.app.coin.Data.DataSource.DataClass.CoinDataClass.DataClassItem
import com.app.coin.Data.DataSource.DataClass.CoinDetailsDataClass.CoinDetails
import retrofit2.Response

interface CoinRepositoriesImp {
    suspend fun getCoins(page:String): Response<DataClass>
    suspend fun getCoinById(id:String):Response<CoinDetails>
}