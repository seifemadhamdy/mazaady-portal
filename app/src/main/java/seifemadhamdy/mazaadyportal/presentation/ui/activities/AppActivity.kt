package seifemadhamdy.mazaadyportal.presentation.ui.activities

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import seifemadhamdy.mazaadyportal.R
import seifemadhamdy.mazaadyportal.databinding.ActivityAppBinding
import seifemadhamdy.mazaadyportal.presentation.ui.viewmodels.AppViewModel

class AppActivity : AppCompatActivity() {
    private val binding: ActivityAppBinding by lazy { ActivityAppBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupWindowInsets()
        setupNavigation()
        setupTabLayout()
        observeViewModel()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private fun addBadgeToTabLayout() {
        binding.tabLayout.getTabAt(2)?.orCreateBadge?.let { badge ->
            badge.apply {
                backgroundColor = ContextCompat.getColor(this@AppActivity, R.color.color_EC5F5F)
                badgeTextColor = Color.WHITE
                number = 2
            }
        }
    }

    private fun setupTabLayout() {
        addBadgeToTabLayout()
        binding.tabLayout.addOnTabSelectedListener(TabSelectionListener())
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.selectedTab.collect { tabItem ->
                when (tabItem) {
                    AppViewModel.TabItem.HOME -> navigateToHomeIfNeeded()
                    AppViewModel.TabItem.FORM -> navigateToFormIfNeeded()
                }
            }
        }
    }

    private inner class TabSelectionListener : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.position?.let { position ->
                if (position == 2) {
                    tab.removeBadge()
                } else {
                    addBadgeToTabLayout()
                }

                viewModel.onTabSelected(position)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    }

    private fun navigateToHomeIfNeeded() {
        val homeDestinationId = navController.graph[R.id.home].id
        if (navController.currentDestination?.id != homeDestinationId) {
            navController.navigate(R.id.action_navigate_to_fragmentHome)
        }
    }

    private fun navigateToFormIfNeeded() {
        val formDestinationId = navController.graph[R.id.form].id
        if (navController.currentDestination?.id != formDestinationId) {
            navController.navigate(R.id.action_navigate_to_fragmentForm)
        }
    }
}
