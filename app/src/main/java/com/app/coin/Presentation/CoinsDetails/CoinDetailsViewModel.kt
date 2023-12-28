package com.app.coin.Presentation.CoinsDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.coin.Data.Repostories.CoinRepository
import com.app.coin.Domain.State.CoinDetailsState
import com.app.coin.Domain.State.ResponseState
import com.app.coin.Domain.UseCases.GetCoinDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val coinsDetailsUseCase: GetCoinDetailsUseCase
):ViewModel() {
    private var _coinDetailsStatFlow= MutableStateFlow(CoinDetailsState())
    var coinDetailsStatFlow: MutableStateFlow<CoinDetailsState> =_coinDetailsStatFlow
    suspend fun getCoinDetails(id:String)
    {
        Log.e("start","viewModel")
        coinsDetailsUseCase(id).collect{
            when(it)
            {
                is ResponseState.Success ->{
                    _coinDetailsStatFlow.value = CoinDetailsState(coinDetails = it.data)
                }
                is ResponseState.Loading ->{
                    _coinDetailsStatFlow.value = CoinDetailsState(isLoading = true)
                }
                is ResponseState.Error ->{
                    _coinDetailsStatFlow.value = CoinDetailsState(error = it.message?:"An Unexpected Error")
                }
                else -> {}
            }
        }

    }
}