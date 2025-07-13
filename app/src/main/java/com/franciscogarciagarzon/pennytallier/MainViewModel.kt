package com.franciscogarciagarzon.pennytallier

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franciscogarciagarzon.domain.categories.GetCategories
import com.franciscogarciagarzon.domain.common.Result
import com.franciscogarciagarzon.domain.model.CategoryDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories,
//    private val getSubcategoriesForCategoryUseCase: GetSubcategoriesForCategory
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<Result<List<CategoryDomain>>>(Result.Loading)
    val categoriesState: StateFlow<Result<List<CategoryDomain>>> =
        _categoriesState.asStateFlow()

//    private val _selectedCategorySubcategoriesState = MutableStateFlow<Result<List<SubCategory>>>(Result.Success(emptyList()))
//    val selectedCategorySubcategoriesState: StateFlow<Result<List<Subcategory>>> = _selectedCategorySubcategoriesState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase(Dispatchers.IO)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = Result.loading(),

                    )
                .collect { result ->
                    when (result) {
                        is Result.Error -> Log.e("MainViewModel", "Error at loadCategories: ${result.exception}")
                        Result.Loading -> Log.d("MainViewModel", "Loading at loadCategories")
                        is Result.Success<Result<List<CategoryDomain>>> -> {
                            _categoriesState.value = result.data
                        }
                    }

//                    if (result is Result.Success && result.data.isNotEmpty()) {
//                        loadSubcategoriesForCategory(result.data.first().id)
//                    }
                }
        }
    }

//    fun loadSubcategoriesForCategory(categoryId: Long) {
//        viewModelScope.launch {
//            getSubcategoriesForCategoryUseCase(categoryId, Dispatchers.IO)
//                .collect { result ->
//                    _selectedCategorySubcategoriesState.value = result
//                }
//        }
//    }
}