package seifemadhamdy.mazaadyportal

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import seifemadhamdy.mazaadyportal.data.remote.api.ApiService
import seifemadhamdy.mazaadyportal.data.remote.dto.OptionsBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.data.repository.api.OptionsRepositoryImpl

class OptionsRepositoryImplTest {

    private val apiService: ApiService = Mockito.mock(ApiService::class.java)
    private val repository = OptionsRepositoryImpl(apiService)

    /**
     * Tests that the `getOptionsChildByOptionId` method returns children of an option by its id
     * when the API response is successful.
     */
    @Test
    fun `getOptionsChildByOptionId returns properties data when response is successful`() =
        runBlocking {
            // Arrange
            val expectedPropertiesData =
                ArrayList<PropertiesDataDto>().apply {
                    add(
                        PropertiesDataDto(
                            id = 1,
                            name = "Color",
                            description = "Product color",
                            slug = "color",
                            parent = null,
                            list = true,
                            type = "dropdown",
                            value = "Red",
                            otherValue = null,
                            options = arrayListOf(),
                        )
                    )
                }

            val response = Response.success(OptionsBaseResponseDto(data = expectedPropertiesData))
            Mockito.`when`(apiService.getOptionsChildByOptionId(1)).thenReturn(response)

            // Act
            val actualPropertiesData = repository.getOptionsChildByOptionId(1)

            // Assert
            assertEquals(expectedPropertiesData, actualPropertiesData)
        }
}
