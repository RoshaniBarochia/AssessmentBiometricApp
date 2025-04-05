package com.app.assessment.db

import androidx.room.*
import com.app.assessment.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<Transaction>)

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()

    @Query("SELECT * FROM transactions WHERE amount LIKE :query OR description LIKE :query OR category LIKE :query OR date LIKE :query ORDER BY date DESC")
    suspend fun searchTransactions(query: String): List<Transaction>
}
