package com.franciscogarciagarzon.domain.categories

import com.franciscogarciagarzon.domain.common.Result
import com.franciscogarciagarzon.domain.model.CategoryDomain
import kotlinx.coroutines.flow.Flow

interface GetCategoriesRepository {
    fun getCategories(): Flow<Result<List<CategoryDomain>>>
}