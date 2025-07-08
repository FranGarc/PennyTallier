package com.franciscogarciagarzon.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "savings",
    foreignKeys = [
        ForeignKey(
            entity = SavingsAccount::class,
            parentColumns = ["id"],
            childColumns = ["savingsAccountId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Saving(
    @PrimaryKey val id: String,
    val amount: Float,
    val date: LocalDate,
    val savingsAccountId: String,
    val description: String,
)
