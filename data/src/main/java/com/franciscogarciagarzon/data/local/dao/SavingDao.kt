package com.franciscogarciagarzon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.franciscogarciagarzon.data.local.model.Saving
import com.franciscogarciagarzon.data.local.model.SavingsAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingDao {
    @Insert
    suspend fun insert(saving: Saving)

    @Update
    suspend fun update(saving: Saving)

    @Query("SELECT * FROM savings ORDER BY savingsAccountId")
    fun getAll(): Flow<List<Saving>>

    @Query("SELECT * FROM savings WHERE id = :id")
    suspend fun getById(id: String): Saving?

}

