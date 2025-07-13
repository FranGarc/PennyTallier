package com.franciscogarciagarzon.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
    ],
    indices = [Index(value = ["categoryId"]), Index(value = ["subCategoryId"])]
)
data class Expense(
    @PrimaryKey val id: String,
    val amount: Float,
    val date: LocalDate,
    val categoryId: Long,
    val subCategoryId: Long,
    val description: String,
)
