package com.franciscogarciagarzon.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings_account")
data class SavingsAccount(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
)
