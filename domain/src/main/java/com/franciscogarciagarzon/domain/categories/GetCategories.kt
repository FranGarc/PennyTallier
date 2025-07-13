package com.franciscogarciagarzon.domain.categories

import com.franciscogarciagarzon.domain.common.Result
import com.franciscogarciagarzon.domain.common.ResultFlowUseCaseWithoutParams
import com.franciscogarciagarzon.domain.model.CategoryDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val repository: GetCategoriesRepository
) : ResultFlowUseCaseWithoutParams<Result<List<CategoryDomain>>>() {
    override fun execute(params: Unit): Flow<Result<List<CategoryDomain>>> {
        return repository.getCategories()
    }
}