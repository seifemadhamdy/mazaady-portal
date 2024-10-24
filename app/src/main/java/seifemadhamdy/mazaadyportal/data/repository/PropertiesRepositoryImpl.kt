package seifemadhamdy.mazaadyportal.data.repository

import retrofit2.Response
import seifemadhamdy.mazaadyportal.data.remote.api.ApiService
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.domain.repository.PropertiesRepository

class PropertiesRepositoryImpl(private val apiService: ApiService) : PropertiesRepository {
    override suspend fun getAllPropertiesBySubCategoryId(
        subCategoryId: Int
    ): List<PropertiesDataDto>? {
        val response: Response<PropertiesBaseResponseDto> =
            apiService.getPropertiesBySubCategoryId(subCategoryId = subCategoryId)

        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}
