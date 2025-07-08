package com.franciscogarciagarzon.domain.model

import java.time.LocalDate

data class SavingDomain(
    val id: String,
    val amount: Float,
    val date: LocalDate,
    val savingsAccountId: String,
    val description: String,
)
