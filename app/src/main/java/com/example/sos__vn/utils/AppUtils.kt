@file:Suppress("DEPRECATION")

package com.example.sos__vn.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class AppUtils {
    companion object {
        fun isNetWorkAvailable(context: Context): Boolean {
            if (context == null) return false
            var connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager == null) return false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                var network = connectivityManager.activeNetwork
                if (network == null) {
                    return false
                }
                var capilities = connectivityManager.getNetworkCapabilities(network)
                return capilities != null && (capilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ))
            } else {
                var networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
        }
    }
}