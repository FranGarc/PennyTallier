package com.franciscogarciagarzon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.franciscogarciagarzon.data.local.model.Category
import com.franciscogarciagarzon.data.local.model.SubCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface SubCategoryDao {
    @Insert
    suspend fun insert(subCategory: SubCategory)

    @Update
    suspend fun update(subCategory: SubCategory)

    @Query("SELECT * FROM subcategories ORDER BY name")
    fun getAll(): Flow<List<SubCategory>>

    @Query("SELECT * FROM subcategories WHERE id = :id")
    suspend fun getById(id: String): Category?

}