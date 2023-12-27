package com.app.coin.Domain.Modeles

data class CoinDetail(val name : String,
                      val image : String,
                      val market_cap : Double,
                      val price : Double,
                      val price_percent_change : Double,
                      val lowPrice : Double,
                      val highPrice : Double,
                      val description : String)
