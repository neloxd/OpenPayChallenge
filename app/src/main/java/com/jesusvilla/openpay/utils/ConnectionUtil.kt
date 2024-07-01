package com.jesusvilla.openpay.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.net.InetAddress


/**
 * Created by Jesús Villa on 18/08/23
 */

class ConnectionUtil(private var mContext: Context) : LifecycleObserver {

    private val TAG = "LOG_TAG"

    companion object NetworkType {

        /**
         * Indicates this network uses a Cellular transport.
         */
        const val TRANSPORT_CELLULAR = 0

        /**
         * Indicates this network uses a Wi-Fi transport.
         */
        const val TRANSPORT_WIFI = 1

    }

    private var mConnectivityMgr: ConnectivityManager? = null

    //    private var mContext: Context? = null
    //private var mNetworkStateReceiver: NetworkStateReceiver? = null

    /*
     * boolean indicates if my device is connected to the internet or not
     * */
    private var mIsConnected = false
    private var mConnectionMonitor: ConnectionMonitor? = null


    /**
     * Indicates there is no available network.
     */
    private val NO_NETWORK_AVAILABLE = -1


    interface ConnectionStateListener {
        fun onAvailable(isAvailable: Boolean)
    }

    init {
        mConnectivityMgr =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        (mContext as AppCompatActivity?)!!.lifecycle.addObserver(this)
        mConnectionMonitor = ConnectionMonitor()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        mConnectivityMgr!!.registerNetworkCallback(networkRequest, mConnectionMonitor!!)

        mIsConnected = isNetworkCheckedConnection()
    }


    /**
     * Returns true if connected to the internet, and false otherwise
     *
     * NetworkInfo is deprecated in API 29
     * https://developer.android.com/reference/android/net/NetworkInfo
     *
     * getActiveNetworkInfo() is deprecated in API 29
     * https://developer.android.com/reference/android/net/ConnectivityManager#getActiveNetworkInfo()
     *
     * getNetworkInfo(int) is deprecated as of API 23
     * https://developer.android.com/reference/android/net/ConnectivityManager#getNetworkInfo(int)
     */
    fun isOnline(): Boolean {
        mIsConnected = false

        val allNetworks = mConnectivityMgr!!.allNetworks // added in API 21 (Lollipop)
        for (network in allNetworks) {
            val networkCapabilities = mConnectivityMgr!!.getNetworkCapabilities(network)
            if (networkCapabilities != null) {
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                            ) mIsConnected = true
            }
        }
        return mIsConnected
    }


    /**
     * Returns:
     *
     * NO_NETWORK_AVAILABLE >>> when you're offline
     * TRANSPORT_CELLULAR >> When Cellular is the active network
     * TRANSPORT_WIFI >> When Wi-Fi is the Active network
     */
    /*fun getActiveNetwork(): Int {
        val activeNetwork = mConnectivityMgr!!.activeNetworkInfo // Deprecated in API 29
        if (activeNetwork != null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = mConnectivityMgr!!.getNetworkCapabilities(
                mConnectivityMgr!!.activeNetwork
            )
            if (capabilities != null) if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // connected to mobile data
                return TRANSPORT_CELLULAR
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // connected to wifi
                return TRANSPORT_WIFI
            }
        } else {
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) { // Deprecated in API 28
                // connected to mobile data
                return TRANSPORT_CELLULAR
            } else if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) { // Deprecated in API 28
                // connected to wifi
                return TRANSPORT_WIFI
            }
        }
        return NO_NETWORK_AVAILABLE
    }*/

    /* Simple getter for fetching network connection status synchronously */
    //fun hasNetworkConnection() = getCurrentNetwork() == NetworkStatusConnected


    private fun getCurrentNetwork(): Boolean {
        return mConnectivityMgr?.activeNetworkInfo?.isConnected == true
        /*val network = mConnectivityMgr!!.getNetworkCapabilities(mConnectivityMgr!!.activeNetwork)
        var msg = ""
        network?.capabilities?.forEachIndexed { index, i ->
            msg+= " " + network.capabilities[index]
        }
        //Toast.makeText(mContext, "network:${msg}", Toast.LENGTH_LONG).show()
        network?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .let { connected ->
                return connected == true
            }*/
    }


    fun getAvailableNetworksCount(): Int {
        var count = 0
        val allNetworks = mConnectivityMgr!!.allNetworks// added in API 21 (Lollipop)

        allNetworks.forEachIndexed { index, network ->
            run {
                val networkCapabilities = mConnectivityMgr!!.getNetworkCapabilities(network)
                networkCapabilities?.let {
                    //Toast.makeText(mContext, "networkCapabilities:${networkCapabilities}", Toast.LENGTH_SHORT).show()
                    val TRANSPORT_CELLULAR =
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    val TRANSPORT_WIFI =
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    val INTERNET =
                            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

                    /*Toast.makeText(
                        mContext,
                        "i:$index CELLULAR:$TRANSPORT_CELLULAR WIFI:$TRANSPORT_WIFI INTERNET:$INTERNET",
                        Toast.LENGTH_SHORT
                    ).show()*/

                    if ((TRANSPORT_CELLULAR || TRANSPORT_WIFI) && INTERNET) {
                        count++
                    }
                }
            }
        }
        return count
    }

    fun getAvailableNetworks(): List<Int> {
        val activeNetworks: MutableList<Int> = ArrayList()
        val allNetworks: Array<Network> = mConnectivityMgr!!.allNetworks // added in API 21 (Lollipop)
            for (network in allNetworks) {
                val networkCapabilities = mConnectivityMgr!!.getNetworkCapabilities(network)
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) activeNetworks.add(
                        TRANSPORT_WIFI
                    )
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) activeNetworks.add(
                        TRANSPORT_CELLULAR
                    )
                }
            }
        return activeNetworks
    }


    fun onInternetStateListener(listener: ConnectionStateListener) {
        mConnectionMonitor!!.setOnConnectionStateListener(listener)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        (mContext as AppCompatActivity?)!!.lifecycle.removeObserver(this)
        if (mConnectionMonitor != null) mConnectivityMgr!!.unregisterNetworkCallback(
            mConnectionMonitor!!
        )
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr = InetAddress.getByName("google.com")
            //You can replace it with your name
            return !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    private fun isNetworkConnected(): Boolean {
        return mConnectivityMgr!!.activeNetworkInfo != null && mConnectivityMgr!!.activeNetworkInfo!!.isConnected
    }

    private fun isNetworkCheckedConnection(): Boolean {
        val activeNetwork = mConnectivityMgr!!.activeNetworkInfo
        val isConnect = activeNetwork?.isConnected
        val isAvailable = activeNetwork?.isAvailable
        val isConnecting = activeNetwork?.isConnectedOrConnecting

        val active = if (activeNetwork != null) {
            // connected to the internet
            when (activeNetwork.type) {
                ConnectivityManager.TYPE_WIFI -> {
                    // connected to wifi
                    true
                }
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> activeNetwork.type == ConnectivityManager.TYPE_MOBILE
            }
        } else {
            // not connected to the internet
            false
        }
        return active && isConnect == true
    }


    inner class NetworkStateReceiver(var mListener: ConnectionStateListener) :
        BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                val activeNetworkInfo: NetworkInfo? =
                    mConnectivityMgr?.getActiveNetworkInfo() // deprecated in API 29

                /*
                 * activeNetworkInfo.getState() deprecated in API 28
                 * NetworkInfo.State.CONNECTED deprecated in API 29
                 * */if (!mIsConnected && activeNetworkInfo != null && activeNetworkInfo.state == NetworkInfo.State.CONNECTED) {
                    mIsConnected = true
                    mListener.onAvailable(true)
                } else if (intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                        java.lang.Boolean.FALSE
                    )
                ) {
                    if (!isOnline()) {
                        mListener.onAvailable(false)
                        mIsConnected = false
                    }
                }
            }
        }
    }

    inner class ConnectionMonitor : ConnectivityManager.NetworkCallback() {
        private var mConnectionStateListener: ConnectionStateListener? = null
        fun setOnConnectionStateListener(connectionStateListener: ConnectionStateListener?) {
            mConnectionStateListener = connectionStateListener
        }

        @RequiresApi(Build.VERSION_CODES.S)
        override fun onAvailable(network: Network) {
            //val availableNetworks =  -1 //getAvailableNetworksCount()
            //Toast.makeText(mContext, "onAvailable:$mIsConnected Current:${getCurrentNetwork()} networks:$availableNetworks", Toast.LENGTH_LONG).show()
            //Toast.makeText(mContext, "onAvailable:$mIsConnected CurrentActive:${getCurrentNetwork()}", Toast.LENGTH_LONG).show()
            if (!mIsConnected) {
                mConnectionStateListener?.let {
                    it.onAvailable(true)
                    mIsConnected = true
                }
            }
        }

        override fun onLost(network: Network) {
            val availableNetworks = getAvailableNetworksCount()
            //Toast.makeText(mContext, "onLost:$mIsConnected networks:${availableNetworks}", Toast.LENGTH_LONG).show()
            val isAvailable = isNetworkCheckedConnection()//getCurrentNetwork()
            //Toast.makeText(mContext, "onLost:$availableNetworks CurrentNetwork:${isAvailable}", Toast.LENGTH_LONG).show()
            if (/*!isAvailable ||*/ availableNetworks == 0) {
                mConnectionStateListener?.onAvailable(false)
                mIsConnected = false
            }
        }
    }

}