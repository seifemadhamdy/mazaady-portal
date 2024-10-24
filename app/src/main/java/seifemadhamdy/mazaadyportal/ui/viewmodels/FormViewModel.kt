package seifemadhamdy.mazaadyportal.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesCategoriesDto
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesChildrenDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.domain.repository.CategoriesRepository
import seifemadhamdy.mazaadyportal.domain.repository.PropertiesRepository

class FormViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val propertiesRepository: PropertiesRepository,
) : ViewModel() {
    private val _mainCategories = MutableLiveData<List<CategoriesCategoriesDto>>()
    val mainCategories: LiveData<List<CategoriesCategoriesDto>> = _mainCategories

    private val _subCategories = MutableLiveData<List<CategoriesChildrenDto>>()
    val subCategories: LiveData<List<CategoriesChildrenDto>> = _subCategories

    private val _subCategoryProperties = MutableLiveData<List<PropertiesDataDto>>()
    val subCategoryProperties: LiveData<List<PropertiesDataDto>> = _subCategoryProperties

    fun fetchMainCategories() {
        viewModelScope.launch { _mainCategories.value = categoriesRepository.getAllCategories() }
    }

    fun updateSubCategories(subCategories: List<CategoriesChildrenDto>) {
        _subCategories.value = subCategories
    }

    fun fetchPropertiesBySubCategoryId(subCategoryId: Int) {
        viewModelScope.launch {
            _subCategoryProperties.value =
                propertiesRepository.getAllPropertiesBySubCategoryId(subCategoryId = subCategoryId)
        }
    }
}
