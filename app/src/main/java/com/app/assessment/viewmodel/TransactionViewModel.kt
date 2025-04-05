package com.app.assessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.assessment.model.Transaction
import com.app.assessment.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {
    private val transactions = MutableLiveData<List<Transaction>>()

    fun getTransactions(): LiveData<List<Transaction>?> {
        return transactions
    }

    fun loadTransactions(token: String?) {
        repository.fetchTransactions(token, object : Callback<List<Transaction>> {
            override fun onResponse(
                call: Call<List<Transaction>>,
                response: Response<List<Transaction>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    transactions.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}*/
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    val transactions: StateFlow<List<Transaction>> = repository
        .allTransactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchResults = MutableStateFlow<List<Transaction>>(emptyList())
    val searchResults: StateFlow<List<Transaction>> = _searchResults

    fun loadTransactions(token: String?) {
        viewModelScope.launch {
            repository.fetchTransactions(token)
        }
    }

    fun searchTransactions(query: String) {
        viewModelScope.launch {
            val result = repository.searchTransactions(query)
            _searchResults.emit(result)
        }
    }
}

