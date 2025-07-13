package com.franciscogarciagarzon.domain.categories

import com.franciscogarciagarzon.domain.model.CategoryDomain

interface NewCategoryRepository {
    suspend fun newCategory(category: CategoryDomain)
}