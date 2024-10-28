package seifemadhamdy.mazaadyportal.data.repository.network

import seifemadhamdy.mazaadyportal.data.remote.network.NetworkConnectivityService
import seifemadhamdy.mazaadyportal.domain.repository.network.NetworkConnectivityRepository

class NetworkConnectivityRepositoryImpl(
    private val networkConnectivityService: NetworkConnectivityService
) : NetworkConnectivityRepository {
    override fun onAvailable(onAvailable: () -> Unit) {
        networkConnectivityService.onAvailable(onAvailable)
    }

    override fun onLost(onLost: () -> Unit) {
        networkConnectivityService.onLost(onLost)
    }

    override fun unregister() {
        networkConnectivityService.unregister()
    }
}
