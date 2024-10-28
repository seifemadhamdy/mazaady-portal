package seifemadhamdy.mazaadyportal.domain.repository.api

import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto

interface PropertiesRepository {
    suspend fun getAllPropertiesBySubCategoryId(subCategoryId: Int): List<PropertiesDataDto>?
}
