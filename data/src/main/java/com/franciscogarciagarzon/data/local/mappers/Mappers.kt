package com.franciscogarciagarzon.data.local.mappers

import com.franciscogarciagarzon.data.local.dao.AccountBalance
import com.franciscogarciagarzon.data.local.model.Category
import com.franciscogarciagarzon.data.local.model.Expense
import com.franciscogarciagarzon.data.local.model.SubCategory
import com.franciscogarciagarzon.domain.model.AccountBalanceDomain
import com.franciscogarciagarzon.domain.model.CategoryDomain
import com.franciscogarciagarzon.domain.model.ExpenseDomain
import com.franciscogarciagarzon.domain.model.SubCategoryDomain


fun Category.toDomain(): CategoryDomain {
    return CategoryDomain(
        id = this.id,
        name = this.name,
        color = this.color,
    )
}

fun CategoryDomain.toData(): Category {
    return Category(
        id = this.id,
        name = this.name,
        color = this.color,
    )
}

fun SubCategory.toDomain(): SubCategoryDomain {
    return SubCategoryDomain(
        id = this.id,
        name = this.name,
        categoryId = this.categoryId
    )
}

fun SubCategoryDomain.toDomain(): SubCategory {
    return SubCategory(
        id = this.id,
        name = this.name,
        categoryId = this.categoryId
    )
}

fun Expense.toDomain(): ExpenseDomain {
    return ExpenseDomain(
        id = this.id,
        amount = this.amount,
        categoryId = this.categoryId,
        subCategoryId = this.subCategoryId,
        date = this.date,
        description = this.description
    )
}

fun ExpenseDomain.toData(): Expense {
    return Expense(
        id = this.id,
        amount = this.amount,
        categoryId = this.categoryId,
        subCategoryId = this.subCategoryId,
        date = this.date,
        description = this.description
    )
}

fun AccountBalance.toDomain(): AccountBalanceDomain {
    return AccountBalanceDomain(
        accountId = this.accountId,
        accountName = this.accountName,
        totalAmount = this.totalAmount ?: 0f,
    )
}

fun AccountBalanceDomain.toData(): AccountBalance {
    return AccountBalance(
        accountId = this.accountId,
        accountName = this.accountName,
        totalAmount = this.totalAmount,
    )
}