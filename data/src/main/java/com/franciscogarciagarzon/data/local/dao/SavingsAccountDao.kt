package com.franciscogarciagarzon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.franciscogarciagarzon.data.local.model.SavingsAccount
import kotlinx.coroutines.flow.Flow

data class AccountBalance(
    val accountId: String,
    val accountName: String,
    val totalAmount: Float? // Make Float nullable if an account might have no savings yet, SUM might return NULL
)


@Dao
interface SavingsAccountDao {

    @Insert
    suspend fun insert(savingsAccount: SavingsAccount)

    @Update
    suspend fun update(savingsAccount: SavingsAccount)

    @Query("SELECT * FROM savings_account ORDER BY name")
    fun getAll(): Flow<List<SavingsAccount>>

    @Query("SELECT * FROM savings_account WHERE id = :id")
    suspend fun getById(id: String): SavingsAccount?

    @Query("SELECT SUM(amount) FROM savings WHERE id = :id")
    suspend fun getBalanceById(id: String): Float?

    @Query("SELECT sa.id AS accountId, sa.name AS accountName, SUM(s.amount) AS totalAmount " +
            "FROM savings_account AS sa " +
            "LEFT JOIN savings AS s ON sa.id = s.savingsAccountId " + // Use LEFT JOIN to include accounts with 0 balance
            "GROUP BY sa.id, sa.name " +
            "ORDER BY sa.name")
    fun getAccountBalances(): Flow<List<AccountBalance>>

}