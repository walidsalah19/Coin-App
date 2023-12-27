package com.app.coin.Domain.Repostrories

import com.app.coin.Data.DataSource.DataClass.DataClass
import com.app.coin.Data.DataSource.DataClass.DataClassItem
import retrofit2.Response

interface CoinRepositoriesImp {
    suspend fun getCoins(page:String): Response<DataClass>
    suspend fun getCoinById(id:String):Response<DataClassItem>
}