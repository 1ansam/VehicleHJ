package com.yxf.vehiclehj

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Environment
import android.widget.Toast

/**
 *   author:yxf
 *   time:2023/2/17
 */
class MyApp : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var manager: ActivityManager
    }

    private fun getCrashLogDir(): String {
        return getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/log"
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        CrashHandler.getInstance(context)?.setCrashLogDir(getCrashLogDir())




    }

}