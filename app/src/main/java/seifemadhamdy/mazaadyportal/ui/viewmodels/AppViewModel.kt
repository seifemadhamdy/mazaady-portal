package seifemadhamdy.mazaadyportal.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow(TabItem.HOME)
    val selectedTab: StateFlow<TabItem> = _selectedTab.asStateFlow()

    enum class TabItem {
        HOME,
        FORM,
    }

    fun onTabSelected(position: Int) {
        _selectedTab.value =
            when (position) {
                0 -> TabItem.HOME
                else -> TabItem.FORM
            }
    }
}
