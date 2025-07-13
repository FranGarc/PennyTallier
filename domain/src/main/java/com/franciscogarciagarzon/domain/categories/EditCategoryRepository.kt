package com.franciscogarciagarzon.domain.categories

import com.franciscogarciagarzon.domain.model.CategoryDomain

interface EditCategoryRepository {
    suspend fun editCategory(category: CategoryDomain)
}