package com.app.coin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.coin.Domain.Modeles.Coin
import com.app.coin.Presentation.Coins.MainViewModel
import com.app.coin.databinding.ActivityMainBinding
import com.example.relevelandroidproject.presentation.CoinList.CoinAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var coinAdapter: CoinAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var page: Int = 1
    private val tempCoinList = arrayListOf<Coin>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutManager = GridLayoutManager(this, 2)
        setUpTheRecyclerView()
        binding.btSort.setOnClickListener {
            tempCoinList.sortWith { o1, o2 -> o1.name.compareTo(o2.name) }
            coinAdapter.setData(tempCoinList)
        }
        getAllCoins(page)
        callAPI()
        binding.coinRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                    page += 1
                    getAllCoins(page)
                    callAPI()
                }
            }
        })

    }
    private fun getAllCoins(page:Int)
    {
        lifecycleScope.launch {
            viewModel.getAllCoins(page.toString())
        }
    }
    private fun callAPI() {
        lifecycleScope.launch {
            //viewModel.getAllCoins(page.toString())
            viewModel.coinStatFlow.collect { value ->
                withContext(Dispatchers.Main) {
                    if (value.isLoading) {

                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        if (value.error.isNotBlank()) {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@MainActivity,
                                value.error,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            binding.progressBar.visibility = View.GONE
                            tempCoinList.addAll(value.coinsList)
                            coinAdapter.setData(tempCoinList as ArrayList<Coin>)
                        }
                    }
                }
            }
        }
    }

    private fun setUpTheRecyclerView() {
        coinAdapter = CoinAdapter(this@MainActivity, ArrayList())
        binding.coinRecyclerView.adapter = coinAdapter
        binding.coinRecyclerView.layoutManager = layoutManager
        binding.coinRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.coinRecyclerView.context,
                (GridLayoutManager(this, 1)).orientation
            )
        )
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.menuSearch)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }
*/
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText?.isEmpty()!!) {
            coinAdapter.setData(tempCoinList)
        } else {
            coinAdapter.filter.filter(newText)
        }
        return true
    }
}