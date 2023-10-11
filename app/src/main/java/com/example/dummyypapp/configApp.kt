package com.example.dummyypapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object ConfigApp {


    fun internetAvailable(context: Context): Boolean {
        val connected:Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED
        return connected
    }



}