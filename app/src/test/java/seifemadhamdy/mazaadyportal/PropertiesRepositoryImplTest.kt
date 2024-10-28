package seifemadhamdy.mazaadyportal

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import seifemadhamdy.mazaadyportal.data.remote.api.ApiService
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesOptionsDto
import seifemadhamdy.mazaadyportal.data.repository.api.PropertiesRepositoryImpl

class PropertiesRepositoryImplTest {

    private val apiService: ApiService = Mockito.mock(ApiService::class.java)
    private val repository = PropertiesRepositoryImpl(apiService)

    /**
     * Tests that the `getAllPropertiesBySubCategoryId` method returns a list of properties for a
     * subcategory by id when the API response is successful.
     */
    @Test
    fun `getAllPropertiesBySubCategoryId returns properties data when response is successful`() =
        runBlocking {
            // Arrange
            val expectedPropertiesData =
                ArrayList<PropertiesDataDto>().apply {
                    add(
                        PropertiesDataDto(
                            id = 1,
                            name = "Brand",
                            description = "Product brand",
                            slug = "brand",
                            parent = null,
                            list = true,
                            type = "dropdown",
                            value = "Apple",
                            otherValue = null,
                            options =
                                arrayListOf(
                                    PropertiesOptionsDto(
                                        id = 1,
                                        name = "Apple",
                                        slug = "apple",
                                        parent = null,
                                        child = false,
                                    ),
                                    PropertiesOptionsDto(
                                        id = 2,
                                        name = "Samsung",
                                        slug = "samsung",
                                        parent = null,
                                        child = false,
                                    ),
                                ),
                        )
                    )
                }

            val response =
                Response.success(PropertiesBaseResponseDto(data = expectedPropertiesData))
            Mockito.`when`(apiService.getPropertiesBySubCategoryId(1)).thenReturn(response)

            // Act
            val actualPropertiesData = repository.getAllPropertiesBySubCategoryId(1)

            // Assert
            assertEquals(expectedPropertiesData, actualPropertiesData)
        }
}
