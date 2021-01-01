package com.justclean.mytask.util

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkConnection(private val context: Context):LiveData<Boolean>() {

    private  var connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var networkCallback:ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
            }
            Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP-> {
            lollipopNetworkRequest()
            }
            else-> {
                context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//                connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())


        }else{

            context.unregisterReceiver(networkReceiver)

        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest(){
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(requestBuilder.build(),connectivityManagerCallback())
    }
    private fun connectivityManagerCallback():ConnectivityManager.NetworkCallback {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = object :ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallback
    }else{
//            connectivityManager.unregisterNetworkCallback(networkCallback)
//            context.unregisterReceiver(networkReceiver)
            throw IllegalAccessError("Error")

        }

    }
    private val networkReceiver = object :BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnection()
        }
    }

private fun updateConnection(){
    var result = false
//    val activeNetwork:NetworkInfo = connectivityManager.activeNetworkInfo
    connectivityManager.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    result = when{
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                        else->false
                    }
                }
            }
        }
    }
    postValue(result)
}
}



















    /* val applicationContext = context.applicationContext
    var result = false
    val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager?.let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    result = when{
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                        else->false
                    }
                }
            }
        }
        return result
    }
}*/

