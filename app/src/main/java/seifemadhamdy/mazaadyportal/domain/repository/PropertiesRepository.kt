package seifemadhamdy.mazaadyportal.domain.repository

import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto

interface PropertiesRepository {
    suspend fun getAllPropertiesBySubCategoryId(subCategoryId: Int): List<PropertiesDataDto>?
}
