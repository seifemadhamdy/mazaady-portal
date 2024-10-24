package seifemadhamdy.mazaadyportal.data.repository

import retrofit2.Response
import seifemadhamdy.mazaadyportal.data.remote.api.ApiService
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesCategoriesDto
import seifemadhamdy.mazaadyportal.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl(private val apiService: ApiService) : CategoriesRepository {
    override suspend fun getAllCategories(): List<CategoriesCategoriesDto>? {
        val response: Response<CategoriesBaseResponseDto> = apiService.getAllCategories()

        return if (response.isSuccessful) {
            response.body()?.data?.categories
        } else {
            null
        }
    }
}
