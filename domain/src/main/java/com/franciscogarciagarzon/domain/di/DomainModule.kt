package com.franciscogarciagarzon.domain.di

import com.franciscogarciagarzon.domain.categories.GetCategories
import com.franciscogarciagarzon.domain.categories.GetCategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Or SingletonComponent::class, etc.
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCategories(
        repository: GetCategoriesRepository
    ): GetCategories {
        return GetCategories(repository)
    }
}