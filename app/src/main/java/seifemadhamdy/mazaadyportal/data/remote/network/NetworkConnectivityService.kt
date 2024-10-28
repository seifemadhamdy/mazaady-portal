package seifemadhamdy.mazaadyportal.data.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class NetworkConnectivityService(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var onAvailableCallback: (() -> Unit)? = null
    private var onLostCallback: (() -> Unit)? = null

    private val networkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onAvailableCallback?.invoke()
            }

            override fun onLost(network: Network) {
                onLostCallback?.invoke()
            }
        }

    init {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    fun onAvailable(onAvailable: () -> Unit) {
        onAvailableCallback = onAvailable
    }

    fun onLost(onLost: () -> Unit) {
        onLostCallback = onLost
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        onAvailableCallback = null
        onLostCallback = null
    }
}
