package seifemadhamdy.mazaadyportal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import seifemadhamdy.mazaadyportal.domain.repository.CategoriesRepository
import seifemadhamdy.mazaadyportal.domain.repository.PropertiesRepository

@Suppress("UNCHECKED_CAST")
class FormViewModelFactory(
    private val categoriesRepository: CategoriesRepository,
    private val propertiesRepository: PropertiesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == FormViewModel::class.java) {
            "Unknown ViewModel class: ${modelClass.simpleName}"
        }
        return FormViewModel(
            categoriesRepository = categoriesRepository,
            propertiesRepository = propertiesRepository,
        )
            as T
    }
}
