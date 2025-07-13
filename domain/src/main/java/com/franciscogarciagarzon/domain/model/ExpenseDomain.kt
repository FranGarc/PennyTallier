package com.franciscogarciagarzon.domain.model

import java.time.LocalDate

data class ExpenseDomain(
    val id: String,
    val amount: Float,
    val date: LocalDate,
    val categoryId: Long,
    val subCategoryId: Long,
    val description: String,
)