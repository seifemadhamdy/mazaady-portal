package seifemadhamdy.mazaadyportal

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import seifemadhamdy.mazaadyportal.data.remote.api.ApiService
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesBaseResponseDto
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesCategoriesDto
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesChildrenDto
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesDataDto
import seifemadhamdy.mazaadyportal.data.repository.api.CategoriesRepositoryImpl

class CategoriesRepositoryImplTest {

    private val apiService: ApiService = Mockito.mock(ApiService::class.java)
    private val repository = CategoriesRepositoryImpl(apiService)

    /**
     * Tests that the `getAllCategories` method returns a list of categories with children when the
     * API response is successful.
     */
    @Test
    fun `getAllCategories returns categories with children when response is successful`() =
        runBlocking {
            // Arrange
            val childCategory =
                CategoriesChildrenDto(
                    id = 101,
                    name = "Mobile Phones",
                    description = "Smartphones and accessories",
                    image = "http://example.com/mobile.png",
                    slug = "mobile-phones",
                    children = null,
                    circleIcon = "http://example.com/icon.png",
                    disableShipping = 0,
                )

            val expectedCategories =
                ArrayList<CategoriesCategoriesDto>().apply {
                    add(
                        CategoriesCategoriesDto(
                            id = 1,
                            name = "Electronics",
                            description = "Devices and gadgets",
                            image = "http://example.com/image.png",
                            slug = "electronics",
                            children = arrayListOf(childCategory),
                            circleIcon = "http://example.com/icon.png",
                            disableShipping = 0,
                        )
                    )
                }

            val response =
                Response.success(
                    CategoriesBaseResponseDto(
                        data = CategoriesDataDto(categories = expectedCategories)
                    )
                )
            Mockito.`when`(apiService.getAllCategories()).thenReturn(response)

            // Act
            val actualCategories = repository.getAllCategories()

            // Assert
            assertEquals(expectedCategories, actualCategories)
        }
}
