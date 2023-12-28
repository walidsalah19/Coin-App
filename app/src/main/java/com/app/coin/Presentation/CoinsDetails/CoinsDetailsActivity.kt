package com.app.coin.Presentation.CoinsDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.coin.Presentation.Coins.MainViewModel
import com.app.coin.R
import com.app.coin.databinding.ActivityCoinsDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CoinsDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCoinsDetailsBinding
    private val coinViewModel: CoinDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCoinsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.let {
            val coinId = it.getStringExtra("id")?:""
            if(coinId.isNotBlank()) {
                getCoinById(coinId)
                observeCoinDetails()
            } else {
                Toast.makeText(this@CoinsDetailsActivity,"We don't have any id to call",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun getCoinById(id:String)
    {
        lifecycleScope.launch {
            coinViewModel.getCoinDetails(id)
        }
    }
    private fun observeCoinDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            coinViewModel.coinDetailsStatFlow.collect{ value ->
                withContext(Dispatchers.Main) {
                    if (value.isLoading) {
                        binding.coinProgressBar.visibility = View.VISIBLE
                    } else if (value.error.isNotBlank()) {
                        binding.coinProgressBar.visibility = View.GONE
                        Toast.makeText(this@CoinsDetailsActivity, value.error, Toast.LENGTH_LONG).show()
                    } else {
                        binding.coinProgressBar.visibility = View.GONE
                        value.coinDetails?.let { coinDetail ->
                            Picasso.get().load(coinDetail.image).into(binding.imgCoinImageDetail)
                            binding.txtCoinPrice.text = "Price : ${coinDetail.price.toString()}"
                            binding.txtCoinName.text = "Coin Name : ${coinDetail.name}"
                            binding.txtCoinPriceLow.text = "Coin Price : ${coinDetail.lowPrice.toString()}"
                            binding.txtCoinPriceHigh.text = "Coin Price High : ${coinDetail.highPrice.toString()}"
                            binding.txtCoinMarketCap.text = "Coin Market Cap : ${coinDetail.market_cap.toString()}"
                            binding.txtCoinPricePercentChange.text =
                                "Coin Price Percent Change : ${coinDetail.price_percent_change.toString()}"
                        }
                    }
                }
            }
        }
    }
}