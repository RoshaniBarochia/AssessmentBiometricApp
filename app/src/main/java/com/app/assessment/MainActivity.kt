package com.app.assessment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.assessment.adapter.TransactionAdapter
import com.app.assessment.databinding.ActivityMainBinding
import com.app.assessment.model.Transaction
import com.app.assessment.viewmodel.TransactionViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var transactionViewModel: TransactionViewModel? = null
    private var adapter: TransactionAdapter? = null

    @Inject
    lateinit var encryptedPrefs: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private val transactions = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = encryptedPrefs.getString("auth_token", "")

        transactionViewModel = ViewModelProvider(this).get(
            TransactionViewModel::class.java
        )
        binding.recyclerView.setLayoutManager(LinearLayoutManager(this))
        setSupportActionBar(binding.toolbar) // Important!


        if (token!!.isNotEmpty()) {
            transactionViewModel!!.loadTransactions(token) // Pass the token to ViewModel
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionViewModel!!.transactions.collect { list ->
                    // update your RecyclerView adapter
                    Log.d("TAG", "onCreate: transaction :: " + Gson().toJson(list))

                    transactions.clear()
                    transactions.addAll(list)
                    setupRecyclerView()
                    setupSearchView()
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionViewModel!!.searchResults.collect { list ->
                    // update your RecyclerView adapter
                    Log.d("TAG", "onCreate: transaction :: " + Gson().toJson(list))

                    transactions.clear()
                    transactions.addAll(list)
                    setupRecyclerView()
                    setupSearchView()
                }
            }
        }
    }
    private fun setupRecyclerView() {
        adapter = TransactionAdapter(transactions)
        binding.recyclerView.setAdapter(adapter)
    }
    private fun setupSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false // Expands search bar on click
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                transactionViewModel!!.searchTransactions(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                transactionViewModel!!.searchTransactions(newText.orEmpty())
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            logoutUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

