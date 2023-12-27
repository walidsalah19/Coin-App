package com.app.coin.Domain.UseCases

import com.app.coin.Data.Repostories.CoinRepository
import com.app.coin.Domain.Modeles.CoinDetail
import com.app.coin.Domain.State.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repo:CoinRepository
) {
   suspend operator fun invoke(id:String):Flow<ResponseState<CoinDetail?>>
    {
        return flow {
            emit(ResponseState.Loading)
            try {
               val response=repo.getCoinById(id)
               if (response.isSuccessful)
               {
                   val coin=response.body()
                   var details= coin?.let {
                       CoinDetail(
                           it.name,
                           it.image,
                           it.market_cap_change_24h,
                           it.current_price,
                           it.price_change_percentage_24h,
                           it.low_24h,
                           it.high_24h,
                           it.symbol
                       )
                   }
                   emit(ResponseState.Success(details))
               }
                else{
                   emit(ResponseState.Error(response.message().toString()))
               }
            }catch (e:Exception)
            {
                emit(ResponseState.Error(e.message.toString()))
            }
        }
    }
}