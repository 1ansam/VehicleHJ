package com.yxf.vehiclehj.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehiclehj.MyApp
import com.yxf.vehiclehj.bean.CommonRequest
import com.yxf.vehiclehj.bean.CommonResponse
import com.yxf.vehicleinspection.singleton.GsonSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 *   author:yxf
 *   time:2023/2/17
 */

const val FILE_PROVIDER = "com.yxf.vehiclehj.fileprovider"
const val VEHICLE_QUEUE = "LYYDJKR101"
const val EXTERIOR_PHOTO = "LYYDJKR102"
const val SYSTEM_PARAMS = "LYYDJKR103"
const val EXTERIOR_ITEM = "LYYDJKR104"
const val WRITE_USER_LOGIN = "LYYDJKW001"
const val SAVE_EXTERIOR_ITEM = "LYYDJKW101"
const val SAVE_EXTERIOR_PHOTO = "LYYDJKW102"

suspend inline fun <reified T> apiCall(crossinline call : suspend CoroutineScope.() -> CommonResponse<T>): CommonResponse<T>{
    return withContext(Dispatchers.IO){
        val res : CommonResponse<T>
        try {
            res = call()
        }catch (e : Throwable){
            return@withContext ApiException.build(e).toResponse<T>()
        }


        return@withContext res
    }
}

/**
 * 将请求实体类包装通用请求jsonString
 * @param element 请求实体类实例化对象
 * @return String
 */
fun <T> getJsonData(element : T) : String{
    val requestArray = ArrayList<T>()
    requestArray.add(element)
    return GsonSingleton.instance.toJson(CommonRequest(requestArray))
}
/**
 * 将请求实体类列表包装为通用请求jsonString
 * @param elements 请求实体类实例化对象列表
 * @return String
 */
fun <T> getJsonData(elements : List<T>) : String{
    val requestArray = ArrayList<T>()
    elements.forEach {
        requestArray.add(it)
    }
//    for (element in elements){
//        requestArray.add(element)
//    }
    return GsonSingleton.instance.toJson(CommonRequest(requestArray))
}

fun getIpAddress() : String {
    val wifiManager : WifiManager = MyApp.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    if (wifiManager.isWifiEnabled){
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return int2Ip(ipAddress)

    }else{
        val handler = Handler(Looper.getMainLooper())
        handler.post{
            showToastOnUiThread(MyApp.context,"WIFI未打开", Toast.LENGTH_SHORT)
        }
        return ""
    }

}

/**
 * 将16进制Int类型Ip地址转为10进制String类型
 * 格式为xxx.xxx.xxx.xxx
 * @param ipAddress 16进制Int类型Ip地址
 * @return String
 */
private fun int2Ip(ipAddress : Int) : String{
    return "${ipAddress and 0XFF}.${ipAddress shr 8 and 0XFF}.${ipAddress shr 16 and 0XFF}.${ipAddress shr 24 and 0XFF}"
}


fun showToastOnUiThread(context: Context, string : String, during : Int){
    val handler = Handler(Looper.getMainLooper())
    handler.post{
        Toast.makeText(context,string, during).show()
    }
}


fun showShortToast (context: Context, string : String){
    Toast.makeText(context,string, Toast.LENGTH_SHORT).show()
}

fun showLongToast (context: Context, string : String){
    Toast.makeText(context,string, Toast.LENGTH_SHORT).show()
}

fun showShortSnackbar(view : View, string: String){
    Snackbar.make(view,string, Snackbar.LENGTH_SHORT).setTextMaxLines(5).show()
}

fun showLongSnackbar(view : View, string: String){
    Snackbar.make(view,string, Snackbar.LENGTH_LONG).setTextMaxLines(5).show()
}

fun bitmap2Base64(bitmap: Bitmap) : String{

    var baos : ByteArrayOutputStream? = null
    try {
        baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        return Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    }catch (e : Exception){
        e.message
    }
    finally {
        baos?.flush()
        baos?.close()
    }
    return ""
}
fun bitmapDrawable2Base64(bitmapDrawable : Drawable) : String{
    return bitmap2Base64(getBitmapFromDrawable(bitmapDrawable))
}

/**
 * 将base64转成Bitmap对象
 * @param base64 base64字符串
 * @return Bitmap对象
 */
fun base642Bitmap(base64: String?) : Bitmap?{
    val bytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
}

/**
 * 从drawable获取Bitmap
 * 多用于ImageView获取Bitmap对象
 * @param drawable Drawable对象
 * @return Bitmap对象
 */
fun getBitmapFromDrawable(drawable : Drawable) : Bitmap {
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0,0,drawable.intrinsicWidth,drawable.intrinsicHeight)
    drawable.draw(canvas)
    return bitmap
}
//DataUtil
/**
 * 将Date对象转为String并格式化
 * @param date Date对象
 * @param format 格式化要求
 * @return String
 */
fun date2String(date: Date, format: String): String {
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format(date)
}
/**
 * 将String转化为Date对象
 * @param stringDate String日期
 * @param format stringDate所对应的格式化要求
 * @return Date
 */
fun string2Date(stringDate: String, format: String): Date {
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.parse(stringDate)
}
/**
 * 格式化StringDate
 * @param stringDate StringDate
 * @param oldFormat 旧格式
 * @param newFormat 新格式
 * @return 新格式的StringDate
 */
fun string2String(stringDate: String,oldFormat: String,newFormat: String) : String{
    var simpleDateFormat = SimpleDateFormat(oldFormat)
//        var simpleDateFormat = SimpleDateFormat(format)
    val date = simpleDateFormat.parse(stringDate)
    simpleDateFormat = SimpleDateFormat(newFormat)
    return simpleDateFormat.format(date)
}

fun isShouldHideKeyboard(v: View,ev : MotionEvent) : Boolean{
    if (v is EditText){
        val leftTop = intArrayOf(0,0)
        v.getLocationInWindow(leftTop)
        val left = leftTop[0]
        val top = leftTop[1]
        val bottom = top + v.height
        val right = left + v.width
        return !(ev.x > left && ev.x < right && ev.y > top && ev.y < bottom)
    }
    return false
}