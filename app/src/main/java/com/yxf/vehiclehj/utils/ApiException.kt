package com.yxf.vehiclehj.utils

import android.util.Log
import com.google.gson.JsonParseException
import com.yxf.vehiclehj.bean.CommonResponse
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *   author:yxf
 *   time:2022/8/10
 */
class ApiException(val code: Int, override val message: String?, override val cause: Throwable? = null)
    : RuntimeException(message, cause) {
    companion object {
        // 网络状态码
        const val CODE_NET_ERROR = 4000
        const val CODE_TIMEOUT = 4080
        const val CODE_JSON_PARSE_ERROR = 4010
        const val CODE_SERVER_ERROR = 5000
        const val CODE_CLIENT_ERROR = 6000
        // 业务状态码
        const val CODE_AUTH_INVALID = 401

        fun build(e: Throwable): ApiException {
            Log.e("Exception", "exception: ${e.printStackTrace()}")
            return if (e is HttpException) {

                ApiException(CODE_NET_ERROR, "网络异常(${e.code()},${e.message()})")
            } else if (e is UnknownHostException) {
                ApiException(CODE_NET_ERROR, "网络连接失败，请检查后再试")
            } else if (e is ConnectTimeoutException || e is SocketTimeoutException) {
                ApiException(CODE_TIMEOUT, "请求超时，请稍后再试")
            } else if (e is IOException) {
                ApiException(CODE_NET_ERROR, "网络异常(${e.message})")
            } else if (e is JsonParseException || e is JSONException) {
                // Json解析失败
                ApiException(CODE_JSON_PARSE_ERROR, "数据解析错误，请稍后再试")
            } else {
                ApiException(CODE_SERVER_ERROR, "系统错误(${e.message})")
            }
        }
    }
    inline fun <reified T> toResponse(): CommonResponse<T> {

        return CommonResponse(ArrayList<T>(),code.toString(), 0, message?:"")
    }
}
