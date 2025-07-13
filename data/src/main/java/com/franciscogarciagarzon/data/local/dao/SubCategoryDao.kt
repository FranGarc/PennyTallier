package com.franciscogarciagarzon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
    suspend fun getById(id: String): SubCategory?

    @Query(
        "SELECT ca.id AS subcategoryId, ca.name AS subcategoryName, SUM(ex_filtered.amount) AS totalAmount " +
                "FROM subcategories AS ca " +
                "LEFT JOIN ( " +
                "    SELECT subCategoryId, amount, date FROM expenses " + // 'date' is the column name for LocalDate
                "    WHERE strftime('%Y', date) = :year AND strftime('%m', date) = :month " +
                ") AS ex_filtered ON ca.id = ex_filtered.subCategoryId " +
                "GROUP BY ca.id, ca.name " +
                "ORDER BY ca.name"
    )
    fun getSubCategorySum(year: String, month: String): Flow<List<SubcategorySum>>

}

data class SubcategorySum(
    val subcategoryId: String,
    val subcategoryName: String,
    val totalAmount: Float? // Make Float nullable if an account might have no savings yet, SUM might return NULL
)