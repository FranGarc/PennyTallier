package com.franciscogarciagarzon.data.di

import com.franciscogarciagarzon.data.categories.CategoriesRepository
import com.franciscogarciagarzon.domain.categories.GetCategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideGetCategoriesRepository(
//        dao: CategoryDao,
        impl: CategoriesRepository,
    ): GetCategoriesRepository
//    {
//        return CategoriesRepository(dao)
//    }

}