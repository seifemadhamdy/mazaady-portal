package seifemadhamdy.mazaadyportal.domain.repository.api

import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto

interface OptionsRepository {
    suspend fun getOptionsChildByOptionId(optionId: Int): List<PropertiesDataDto>?
}
