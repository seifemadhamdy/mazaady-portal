package seifemadhamdy.mazaadyportal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import seifemadhamdy.mazaadyportal.domain.repository.api.CategoriesRepository
import seifemadhamdy.mazaadyportal.domain.repository.api.PropertiesRepository
import seifemadhamdy.mazaadyportal.domain.repository.network.NetworkConnectivityRepository

@Suppress("UNCHECKED_CAST")
class FormViewModelFactory(
    private val categoriesRepository: CategoriesRepository,
    private val propertiesRepository: PropertiesRepository,
    private val networkConnectivityRepository: NetworkConnectivityRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == FormViewModel::class.java) {
            "Unknown ViewModel class: ${modelClass.simpleName}"
        }
        return FormViewModel(
            categoriesRepository = categoriesRepository,
            propertiesRepository = propertiesRepository,
            networkConnectivityRepository = networkConnectivityRepository,
        )
            as T
    }
}
