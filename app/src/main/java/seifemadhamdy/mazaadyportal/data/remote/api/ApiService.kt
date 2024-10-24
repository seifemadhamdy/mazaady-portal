package seifemadhamdy.mazaadyportal.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.OptionsBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesBaseResponseDto

interface ApiService {
    @GET("get_all_cats") suspend fun getAllCategories(): Response<CategoriesBaseResponseDto>

    @GET("properties")
    suspend fun getPropertiesBySubCategoryId(
        @Query("cat") subCategoryId: Int
    ): Response<PropertiesBaseResponseDto>

    @GET("get-options-child/{optionId}")
    suspend fun getOptionsChildByOptionId(
        @Path("optionId") optionId: Int
    ): Response<OptionsBaseResponseDto>
}
