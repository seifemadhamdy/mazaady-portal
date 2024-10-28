package seifemadhamdy.mazaadyportal.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesCategoriesDto
import seifemadhamdy.mazaadyportal.data.remote.dto.CategoriesChildrenDto
import seifemadhamdy.mazaadyportal.data.remote.dto.PropertiesDataDto
import seifemadhamdy.mazaadyportal.domain.repository.api.CategoriesRepository
import seifemadhamdy.mazaadyportal.domain.repository.api.PropertiesRepository
import seifemadhamdy.mazaadyportal.domain.repository.network.NetworkConnectivityRepository
import seifemadhamdy.mazaadyportal.ui.models.SelectedPropertyItem

class FormViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val propertiesRepository: PropertiesRepository,
    private val networkConnectivityRepository: NetworkConnectivityRepository,
) : ViewModel() {
    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable

    private val _mainCategories = MutableLiveData<List<CategoriesCategoriesDto>>()
    val mainCategories: LiveData<List<CategoriesCategoriesDto>> = _mainCategories

    private val _subCategories = MutableLiveData<List<CategoriesChildrenDto>>()
    val subCategories: LiveData<List<CategoriesChildrenDto>> = _subCategories

    private val _subCategoryProperties = MutableLiveData<List<PropertiesDataDto>>()
    val subCategoryProperties: LiveData<List<PropertiesDataDto>> = _subCategoryProperties

    private val _selectedProperties =
        MutableLiveData<MutableList<SelectedPropertyItem>>(mutableListOf())
    val selectedProperties: LiveData<MutableList<SelectedPropertyItem>> = _selectedProperties

    init {
        viewModelScope.launch {
            networkConnectivityRepository.onAvailable { _isNetworkAvailable.postValue(true) }
            networkConnectivityRepository.onLost { _isNetworkAvailable.postValue(false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkConnectivityRepository.unregister()
    }

    fun fetchMainCategories() {
        if (isNetworkAvailable.value == true)
            viewModelScope.launch {
                _mainCategories.value = categoriesRepository.getAllCategories()
            }
    }

    fun updateSubCategories(subCategories: List<CategoriesChildrenDto>) {
        _subCategories.value = subCategories
    }

    fun fetchPropertiesBySubCategoryId(subCategoryId: Int) {
        if (isNetworkAvailable.value == true)
            viewModelScope.launch {
                resetSelectedProperties()

                _subCategoryProperties.value =
                    propertiesRepository.getAllPropertiesBySubCategoryId(
                        subCategoryId = subCategoryId
                    )
            }
    }

    fun updateSelectedProperties(selectedPropertyItem: SelectedPropertyItem) {
        val currentList = _selectedProperties.value.orEmpty().toMutableList()

        val propertyIndex =
            selectedProperties.value?.indexOfFirst {
                it.parentName == selectedPropertyItem.parentName
            } ?: -1

        if (propertyIndex >= 0) {
            currentList[propertyIndex] = selectedPropertyItem
        } else {
            currentList.add(selectedPropertyItem)
        }

        _selectedProperties.value = currentList
    }

    fun resetSelectedProperties() {
        _selectedProperties.value?.clear()
        _selectedProperties.value = mutableListOf()
    }

    fun resetMainCategories() {
        _mainCategories.value = mutableListOf()
    }

    fun resetSubCategories() {
        _subCategories.value = mutableListOf()
    }
}
