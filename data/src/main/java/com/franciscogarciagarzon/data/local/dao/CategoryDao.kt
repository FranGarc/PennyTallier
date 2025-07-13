package com.franciscogarciagarzon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.franciscogarciagarzon.data.local.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category): Long

    @Update
    suspend fun update(category: Category)

    @Query("SELECT * FROM categories ORDER BY name")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): Category?

    @Query(
        "SELECT ca.id AS cateogryId, ca.name AS categoryName, SUM(ex_filtered.amount) AS totalAmount " +
                "FROM categories AS ca " +
                "LEFT JOIN ( " +
                "    SELECT categoryId, amount, date FROM expenses " + // 'date' is the column name for LocalDate
                "    WHERE strftime('%Y', date) = :year AND strftime('%m', date) = :month " +
                ") AS ex_filtered ON ca.id = ex_filtered.categoryId " +
                "GROUP BY ca.id, ca.name " +
                "ORDER BY ca.name"
    )
    fun getCategorySum(year: String, month: String): Flow<List<CategorySum>>

}

data class CategorySum(
    val cateogryId: String,
    val categoryName: String,
    val totalAmount: Float? // Make Float nullable if an account might have no savings yet, SUM might return NULL
)