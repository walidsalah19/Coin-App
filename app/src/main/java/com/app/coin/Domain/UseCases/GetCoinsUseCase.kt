package com.app.coin.Domain.UseCases

import android.util.Log
import com.app.coin.Data.Repostories.CoinRepository
import com.app.coin.Domain.Modeles.Coin
import com.app.coin.Domain.State.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repo:CoinRepository
) {
    suspend operator fun invoke(page:String):Flow<ResponseState<List<Coin>?>>{
        Log.e("start","useCase")

        return flow{
            emit(ResponseState.Loading)
            try {
                var response=repo.getCoins(page)
                if (response.isSuccessful)
                {
                    var coin= response.body()?.map {
                        it.toCoin()
                    }
                    emit(ResponseState.Success(coin))
                }
                else{
                    emit(ResponseState.Error(response.message()))
                }
            }catch (e:Exception)
            {
                emit(ResponseState.Error(e.toString()))
            }
        }
    }
}