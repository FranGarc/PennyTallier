package com.franciscogarciagarzon.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubCategory::class,
            parentColumns = ["id"],
            childColumns = ["subCategoryId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Expense(
    @PrimaryKey val id: String,
    val amount: Float,
    val date: LocalDate,
    val categoryId: String,
    val subCategoryId: String,
    val description: String,
)
