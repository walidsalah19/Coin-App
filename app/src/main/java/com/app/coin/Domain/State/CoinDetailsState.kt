package com.app.coin.Domain.State

import com.app.coin.Domain.Modeles.Coin
import com.app.coin.Domain.Modeles.CoinDetail

data class CoinDetailsState(
    val isLoading : Boolean = false,
    val coinDetails : CoinDetail ?= null,
    val error : String = ""
)