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
import com.yxf.vehiclehj.repo.DatabaseRepo
import com.yxf.vehiclehj.repo.ExteriorRepo
import com.yxf.vehiclehj.repo.QueueRepo
import com.yxf.vehiclehj.repo.UserRepo
import com.yxf.vehiclehj.room.DataDictionaryDatabase
import java.util.Queue

/**
 *   author:yxf
 *   time:2023/2/17
 */
class MyApp : Application() {
    private val database by lazy { DataDictionaryDatabase.getDatabase(context) }
    val userRepo by lazy{ UserRepo()}
    val queueRepo by lazy{ QueueRepo()}
    val exteriorRepo by lazy{ ExteriorRepo()}
    val databaseRepo by lazy{ DatabaseRepo(database.systemParamsDao())}

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
        //取消下面这行可以收集Stacktrack写入到/Android/data/<packageName>/Download/log目录下 后期维护只需让客户发报错文件即可
//        CrashHandler.getInstance(context)?.setCrashLogDir(getCrashLogDir())




    }

}