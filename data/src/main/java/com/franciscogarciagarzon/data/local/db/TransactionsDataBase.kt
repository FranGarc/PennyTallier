package com.franciscogarciagarzon.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.franciscogarciagarzon.data.local.dao.CategoryDao
import com.franciscogarciagarzon.data.local.dao.ExpenseDao
import com.franciscogarciagarzon.data.local.dao.SavingsAccountDao
import com.franciscogarciagarzon.data.local.dao.SubCategoryDao
import com.franciscogarciagarzon.data.local.model.Category
import com.franciscogarciagarzon.data.local.model.Expense
import com.franciscogarciagarzon.data.local.model.Saving
import com.franciscogarciagarzon.data.local.model.SavingsAccount
import com.franciscogarciagarzon.data.local.model.SubCategory

@Database(
    entities = [
        Category::class,
        SubCategory::class,
        Expense::class,
        SavingsAccount::class,
        Saving::class
    ],
    version = 1
)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun CategoryDao(): CategoryDao
    abstract fun subCategoryDao(): SubCategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun savingsDao(): SavingsAccountDao
}

// Database provider
object DatabaseProvider {
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
}