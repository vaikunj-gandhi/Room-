package com.vaikunj.example.util


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


@Suppress("DEPRECATION")
class MyUtils {
    companion object {
        @SuppressLint("MissingPermission")
        fun isInternetAvailable(ctx: Context): Boolean {
            val check = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info = check.allNetworkInfo
            for (i in info.indices)
                if (info[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            return false
        }


    }


}





