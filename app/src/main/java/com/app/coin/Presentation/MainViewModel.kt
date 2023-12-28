package com.app.coin.Presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.coin.Domain.Modeles.Coin
import com.app.coin.Domain.State.CoinDetailsState
import com.app.coin.Domain.State.CoinListState
import com.app.coin.Domain.State.ResponseState
import com.app.coin.Domain.UseCases.GetCoinDetailsUseCase
import com.app.coin.Domain.UseCases.GetCoinsUseCase
import com.google.android.material.badge.BadgeState.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coinsUseCase: GetCoinsUseCase,
    private val coinsDetailsUseCase: GetCoinDetailsUseCase

):ViewModel(){
    private var _coinStatFlow= MutableStateFlow(CoinListState())
    var coinStatFlow: MutableStateFlow<CoinListState> =_coinStatFlow

    private var _coinDetailsStatFlow= MutableStateFlow(CoinDetailsState())
    var coinDetailsStatFlow: MutableStateFlow<CoinDetailsState> =_coinDetailsStatFlow
    suspend fun getAllCoins(page:String)
    {
        Log.e("start","viewModel")
        coinsUseCase(page).collect{
            when(it)
            {
                is ResponseState.Success -> {
                    _coinStatFlow.value= CoinListState(coinsList = it.toData()?: emptyList())
                }
                is ResponseState.Error->
                {
                    _coinStatFlow.value= CoinListState(error = it.message)
                }
                is ResponseState.Loading->
                {
                    _coinStatFlow.value= CoinListState(isLoading = true)
                }
                else -> {}
            }
        }

    }
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