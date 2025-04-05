package com.app.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction (

    @PrimaryKey
    val id: String = "1",
    val amount: Double = 0.0,
    val date: String? = null,
    val category: String? = null,
    val description: String? = null
)
