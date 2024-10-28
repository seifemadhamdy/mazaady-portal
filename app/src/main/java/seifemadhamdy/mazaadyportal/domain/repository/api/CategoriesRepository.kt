package seifemadhamdy.mazaadyportal.domain.repository.api

import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesCategoriesDto

interface CategoriesRepository {
    suspend fun getAllCategories(): List<CategoriesCategoriesDto>?
}
