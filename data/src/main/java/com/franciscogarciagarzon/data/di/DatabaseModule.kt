package com.franciscogarciagarzon.data.di

import android.content.Context
import com.franciscogarciagarzon.data.local.dao.CategoryDao
import com.franciscogarciagarzon.data.local.dao.SubCategoryDao
import com.franciscogarciagarzon.data.local.db.DatabaseProvider
import com.franciscogarciagarzon.data.local.db.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        // SupervisorJob ensures that if a child coroutine fails, it doesn't cancel the parent scope
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        applicationScope: CoroutineScope
    ): TransactionDatabase {
        return DatabaseProvider.getDatabase(context, applicationScope)
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: TransactionDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideSubcategoryDao(appDatabase: TransactionDatabase): SubCategoryDao {
        return appDatabase.subCategoryDao()
    }

}