package com.franciscogarciagarzon.pennytallier

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.franciscogarciagarzon.data.local.model.CategoryColor
import com.franciscogarciagarzon.domain.common.Result
import com.franciscogarciagarzon.domain.model.CategoryDomain
import com.franciscogarciagarzon.domain.model.SubCategoryDomain
import com.franciscogarciagarzon.pennytallier.ui.theme.PennyTallierTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Mark Activity for Hilt injection
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PennyTallierTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Hilt will automatically provide the ViewModel
                    val viewModel: MainViewModel = hiltViewModel()
                    CategoryScreen(viewModel = viewModel)
                }
            }
        }
    }
}

// CategoryScreen, CategoryItem, SubcategoryItem remain the same as before
// The @Preview function needs to be updated to use a dummy ViewModel, as Hilt doesn't run in previews.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(viewModel: MainViewModel) {
//    val categoriesState by viewModel.categoriesState.collectAsState()
    val categoriesResult: Result<List<CategoryDomain>> by viewModel.categoriesState.collectAsState()
    //    val subcategoriesState by viewModel.selectedCategorySubcategoriesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Categories & Subcategories") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Categories:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            when (categoriesResult) {
                is Result.Loading -> CircularProgressIndicator()
                is Result.Error -> Text("Error loading categories: ${(categoriesResult as Result.Error).exception.message}")
                is Result.Success -> {
                    val categories = (categoriesResult as Result.Success<List<CategoryDomain>>).data
                    if (categories.isEmpty()) {
                        Text("No categories found.")
                    } else {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(categories.count()) { index ->
                                CategoryItem(category = categories[index]) {}
                            }
//                            categories.forEach { category ->
//                                CategoryItem(category = category) {
////                                    viewModel.loadSubcategoriesForCategory(category.id)
//                                }
//                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Subcategories:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
//            when (subcategoriesState) {
//                is Result.Loading -> CircularProgressIndicator()
//                is Result.Error -> Text("Error loading subcategories: ${(subcategoriesState as Result.Error).exception.message}")
//                is Result.Success -> {
//                    val subcategories = (subcategoriesState as Result.Success).data
//                    if (subcategories.isNullOrEmpty()) {
//                        Text("No subcategories selected or found.")
//                    } else {
//                        LazyColumn(modifier = Modifier.weight(1f)) {
//                            items(subcategories) { subcategory ->
//                                SubcategoryItem(subcategory = subcategory)
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryDomain, onClick: (CategoryDomain) -> Unit) {

    val categoryColorString = if (category.color.isEmpty()) {
        CategoryColor.ORANGE_LIGHT.hexCode
    } else {
        category.color
    }
    Log.d("MainActivity", "categoryColorString: $categoryColorString")
    val colorString = if (categoryColorString.startsWith("0x", ignoreCase = true)) {
        categoryColorString.substring(2)
    } else {
        categoryColorString
    }
    val backgroundColor = Color(colorString.toLong(16))
    Log.d("MainActivity", "backgroundColor: $backgroundColor")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(category) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Text(
            text = category.name,
            modifier = Modifier
                .padding(16.dp)
                ,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SubcategoryItem(subcategory: SubCategoryDomain) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Text(
            text = subcategory.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    PennyTallierTheme {
//        // Provide a dummy ViewModel for preview purposes
//        // Hilt does not run in previews, so manual instantiation is needed.
////        val dummyViewModel = MainViewModel(
////            getCategoriesUseCase = object : GetCategories(object : GetCategoriesRepository {
////                override fun getCategories(): Flow<List<Category>> = flow {
////                    emit(listOf(CategoryDomain(1, "Preview Category 1"), CategoryDomain(2, "Preview Category 2")))
////                }
////                override fun getSubcategoriesForCategory(categoryId: Long): Flow<List<Subcategory>> = flow {
////                    // Not used in this specific preview path, but required by interface
////                    emit(emptyList())
////                }
//            }) {}, // Empty object for ResultFlowUseCaseWithoutParams
////            getSubcategoriesForCategoryUseCase = object : GetSubcategoriesForCategoryUseCase(object : CategoryRepository {
////                override fun getCategories(): Flow<List<Category>> = flow { }
////                override fun getSubcategoriesForCategory(categoryId: Long): Flow<List<Subcategory>> = flow {
////                    emit(listOf(Subcategory(101, "Preview Sub 1", categoryId), Subcategory(102, "Preview Sub 2", categoryId)))
////                }
////            }) {} // Empty object for ResultFlowUseCase
////        )
////        CategoryScreen(viewModel = dummyViewModel)
//    }
//}
