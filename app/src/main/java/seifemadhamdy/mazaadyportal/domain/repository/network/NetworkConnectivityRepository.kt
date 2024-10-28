package seifemadhamdy.mazaadyportal.domain.repository.network

interface NetworkConnectivityRepository {
    fun onAvailable(onAvailable: () -> Unit)

    fun onLost(onLost: () -> Unit)

    fun unregister()
}
