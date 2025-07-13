package com.franciscogarciagarzon.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.franciscogarciagarzon.data.R
import com.franciscogarciagarzon.data.local.dao.CategoryDao
import com.franciscogarciagarzon.data.local.dao.ExpenseDao
import com.franciscogarciagarzon.data.local.dao.SavingsAccountDao
import com.franciscogarciagarzon.data.local.dao.SubCategoryDao
import com.franciscogarciagarzon.data.local.model.Category
import com.franciscogarciagarzon.data.local.model.Expense
import com.franciscogarciagarzon.data.local.model.Saving
import com.franciscogarciagarzon.data.local.model.SavingsAccount
import com.franciscogarciagarzon.data.local.model.SubCategory
import com.franciscogarciagarzon.data.local.model.converters.DateConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Category::class,
        SubCategory::class,
        Expense::class,
        SavingsAccount::class,
        Saving::class
    ],
    exportSchema = true,
    version = 1
)
@TypeConverters(DateConverters::class,)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun subCategoryDao(): SubCategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun savingsDao(): SavingsAccountDao

    companion object {


    }
}


// Database provider
object DatabaseProvider {
    @Volatile

    private var INSTANCE: TransactionDatabase? = null

    fun getDatabase(context: Context): TransactionDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                TransactionDatabase::class.java,
                "penny_tallier.db"
            ).build().also { INSTANCE = it }
        }
    }

    fun getDatabase(context: Context, scope: CoroutineScope): TransactionDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TransactionDatabase::class.java,
                "app_database" // Your database file name
            )
                .addCallback(TransactionDatabaseCallback(context, scope)) // Add the prepopulation callback
                .build()
            INSTANCE = instance
            instance
        }
    }

    // --- Database Callback for Prepopulation ---
    private class TransactionDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Perform database operations on a background thread
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.categoryDao(), database.subCategoryDao(), context)
                }
            }
        }

        private suspend fun populateDatabase(
            categoryDao: CategoryDao,
            subcategoryDao: SubCategoryDao,
            context: Context
        ) {
            // Retrieve localized category names from strings.xml
            // The Android resource system automatically loads the correct locale.
            val categoryNames = context.resources.getStringArray(R.array.categories_list)

            // Map to store category name -> generated ID for foreign key linking
            val categoryNameToIdMap = mutableMapOf<String, Long>()

            // 1. Insert Categories
            categoryNames.forEach { categoryName ->
                val categoryEntity = Category(name = categoryName, color = "")
                val categoryId = categoryDao.insert(categoryEntity)
                categoryNameToIdMap[categoryName] = categoryId
            }

            // 2. Define Subcategory Mappings (map category name to its corresponding subcategory string array)
            // IMPORTANT: Use the *default language* category names here to map to resource IDs.
            // The `context.resources.getStringArray()` call will then fetch the localized subcategories.
            val subcategoryResourceMap = mapOf(
                "Home" to R.array.subcategories_home,
                "Utilities" to R.array.subcategories_home,
                "Shopping" to R.array.subcategories_shopping,
                "Health" to R.array.subcategories_health,
                "Leisure" to R.array.subcategories_leisure,
                "Transport" to R.array.subcategories_transport,
                "Communication" to R.array.subcategories_communication,
                // Add more mappings for your categories
            )

            // 3. Insert Subcategories, linking them via categoryId
            categoryNameToIdMap.forEach { (categoryName, categoryId) ->
                val subcategoryArrayResId = subcategoryResourceMap[categoryName]
                if (subcategoryArrayResId != null) {
                    val subcategoryNames = context.resources.getStringArray(subcategoryArrayResId)
                    val subcategoriesToInsert = subcategoryNames.map { subName ->
                        SubCategory(name = subName, categoryId = categoryId)
                    }
                    subcategoriesToInsert.forEach { subcategoryDao.insert(it) }

                }
            }
        }
    }
}