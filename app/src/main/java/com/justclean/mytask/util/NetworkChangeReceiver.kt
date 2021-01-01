package com.justclean.mytask.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkChangeReceiver  : BroadcastReceiver() {



    companion object{

        var connectivityReceiverListener:ConnectivityReceiverListener?=null

    }
   /* fun NetworkChangeReceiver(){
        super()
    }*/
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        try {
            if(!isOnline(context)){
                connectivityReceiverListener?.OnNetworkChange(true)
            }
        }catch (e:Exception){

        }

    }
    fun isOnline(context: Context):Boolean{
        var result = false
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm?.let {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    it.getNetworkCapabilities(cm.activeNetwork)?.apply {
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

        }catch (e:Exception){
            return result
        }
    }

    interface ConnectivityReceiverListener{
            fun OnNetworkChange(isConnected:Boolean)
    }
}