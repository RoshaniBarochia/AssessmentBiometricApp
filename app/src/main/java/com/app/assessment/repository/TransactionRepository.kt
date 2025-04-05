package com.app.assessment.repository

import android.util.Log
import com.app.assessment.db.TransactionDao
import com.app.assessment.model.Transaction
import com.app.assessment.network.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/*@Singleton
class TransactionRepository @Inject constructor(private val apiService: ApiService) {
    fun fetchTransactions(token: String?, callback: Callback<List<Transaction>>) {
        apiService.getTransactions(token).enqueue(callback)
    }
}*/

@Singleton
class TransactionRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: TransactionDao
) {
    val allTransactions: Flow<List<Transaction>> = dao.getAllTransactions()

    suspend fun searchTransactions(query: String): List<Transaction> {
        return dao.searchTransactions("%$query%")
    }

    suspend fun fetchTransactions(token: String?) {
        try {
            val response = apiService.getTransactions(token)
            if (response.isSuccessful && response.body() != null) {
                val transactions = response.body()!!
                val entities = transactions.map {
                    Transaction(
                        id = it.id ?: UUID.randomUUID().toString(),
                        amount = it.amount,
                        date = it.date,
                        category = it.category,
                        description = it.description
                    )
                }
                dao.deleteAll()
                dao.insertAll(entities)
            } else {
                Log.e("Repository", "API error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("Repository", "Network error: ${e.localizedMessage}", e)
        }
    }
}




