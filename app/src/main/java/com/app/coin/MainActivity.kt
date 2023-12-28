package com.app.coin

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.coin.Presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // viewModel= ViewModelProvider(this)[MainViewModel::class.java]

        /*lifecycleScope.launch(Dispatchers.IO) {

            viewModel.getAllCoins("1")

            viewModel.coinStatFlow.collect { state ->
                if (state.isLoading) {
                    Log.e("loading", "Loading")
                } else if (state.coinsList.isNotEmpty()) {
                    Log.e("coinsId", state.coinsList.get(0).id)
                } else {
                    Log.e("error", state.error)
                }
            }
        }*/
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getCoinDetails("bitcoin")

            viewModel.coinDetailsStatFlow.collect { state ->
                if (state.isLoading) {
                    Log.e("details", "Loading")
                } else if (state.coinDetails?.name?.isNotEmpty() == true) {
                    state.coinDetails?.let { Log.e("detailsDes", it.description) }
                } else {
                    Log.e("detailsError", state.error)
                }
            }
        }
    }
}