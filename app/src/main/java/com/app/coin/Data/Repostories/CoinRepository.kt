package com.app.coin.Data.Repostories

import com.app.coin.Data.DataSource.CoinInterface
import com.app.coin.Data.DataSource.DataClass.DataClass
import com.app.coin.Data.DataSource.DataClass.DataClassItem
import com.app.coin.Domain.Repostrories.CoinRepositoriesImp
import retrofit2.Response
import javax.inject.Inject

class CoinRepository @Inject constructor(
   private val apiService:CoinInterface
) : CoinRepositoriesImp{
    override suspend fun getCoins(page: String): Response<DataClass> {
       return apiService.getCoin(page)
    }

    override suspend fun getCoinById(id: String): Response<DataClassItem> {
       return apiService.getCoinById(id)
    }
}