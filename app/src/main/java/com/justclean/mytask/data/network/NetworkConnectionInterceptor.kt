package com.justclean.mytask.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.justclean.mytask.util.NoInternetException
import dagger.hilt.android.qualifiers.ActivityContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor constructor( context: Context):Interceptor{
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
            if(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    !isInternetAvailable()
                } else {
                    TODO("VERSION.SDK_INT < LOLLIPOP")
                }
            ){
                throw NoInternetException("Make sure you have an internet connection")
            }

        return chain.proceed(chain.request())
    }
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun isInternetAvailable():Boolean{
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
        }

}