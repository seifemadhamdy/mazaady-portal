package seifemadhamdy.mazaadyportal.data.repository.api

import retrofit2.Response
import seifemadhamdy.mazaadyportal.data.remote.api.ApiService
import seifemadhamdy.mazaadyportal.data.remote.dto.OptionsBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.domain.repository.api.OptionsRepository

class OptionsRepositoryImpl(private val apiService: ApiService) : OptionsRepository {
    override suspend fun getOptionsChildByOptionId(optionId: Int): List<PropertiesDataDto>? {
        val response: Response<OptionsBaseResponseDto> =
            apiService.getOptionsChildByOptionId(optionId = optionId)

        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}
