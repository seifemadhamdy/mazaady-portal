package seifemadhamdy.mazaadyportal.domain.repository

import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesCategoriesDto

interface CategoriesRepository {
    suspend fun getAllCategories(): List<CategoriesCategoriesDto>?
}
