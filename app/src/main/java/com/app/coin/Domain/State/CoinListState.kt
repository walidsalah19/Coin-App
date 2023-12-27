package com.app.coin.Domain.State

import com.app.coin.Domain.Modeles.Coin

data class CoinListState(
    val isLoading : Boolean = false,
    val coinsList : List<Coin> = emptyList(),
    val error : String = ""
)
