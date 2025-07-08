package com.franciscogarciagarzon.domain.model

data class AccountBalanceDomain(
    val accountId: String,
    val accountName: String,
    val totalAmount: Float
)
