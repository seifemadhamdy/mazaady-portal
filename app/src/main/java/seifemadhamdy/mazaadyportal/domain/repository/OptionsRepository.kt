package seifemadhamdy.mazaadyportal.domain.repository

import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto

interface OptionsRepository {
    suspend fun getOptionsChildByOptionId(optionId: Int): List<PropertiesDataDto>?
}
