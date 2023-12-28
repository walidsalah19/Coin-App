package com.app.coin.Domain.Repostrories

import com.app.coin.Data.DataSource.DataClass.CoinDataClass.DataClass
import com.example.relevelandroidproject.data.data_source.dto.CoinDetailDTO.CoinDetailDto
import retrofit2.Response

interface CoinRepositoriesImp {
    suspend fun getCoins(page:String): Response<DataClass>
    suspend fun getCoinById(id:String):Response<CoinDetailDto>
}