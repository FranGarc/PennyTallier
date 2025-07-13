package com.franciscogarciagarzon.data.categories

import android.util.Log
import com.franciscogarciagarzon.data.local.dao.CategoryDao
import com.franciscogarciagarzon.data.local.mappers.toData
import com.franciscogarciagarzon.data.local.mappers.toDomain
import com.franciscogarciagarzon.domain.categories.EditCategoryRepository
import com.franciscogarciagarzon.domain.categories.GetCategoriesRepository
import com.franciscogarciagarzon.domain.categories.NewCategoryRepository
import com.franciscogarciagarzon.domain.model.CategoryDomain
import com.franciscogarciagarzon.domain.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesRepository  @Inject constructor(
    private val categoryDao: CategoryDao,
) : GetCategoriesRepository, NewCategoryRepository, EditCategoryRepository {

    override fun getCategories(): Flow<Result<List<CategoryDomain>>> {
        return categoryDao.getAll()
            .map { entities ->
                val domainCategories: List<CategoryDomain> = entities.map { entity ->
                    entity.toDomain()
                }
                Result.Success(domainCategories)
            }
            .catch { e: Throwable ->
                Log.e("CategoryDao", "exception in getCategories: ${e.message}")
                Result.Error(e)
            }
    }


    override suspend fun newCategory(category: CategoryDomain) {
        categoryDao.insert(category.toData())
    }

    override suspend fun editCategory(category: CategoryDomain) {
        categoryDao.update(category.toData())
    }
}