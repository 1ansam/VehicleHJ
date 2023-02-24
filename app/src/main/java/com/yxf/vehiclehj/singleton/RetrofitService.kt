package com.yxf.vehicleinspection.singleton

import com.yxf.vehicleinspection.base.BaseUrlHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *   author:yxf
 *   time:2021/9/29
 *   封装了retrofit
 *   用法 RetrofitService.create(<Service::class.java>).<function>
 *
 */
object RetrofitService {


    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BaseUrlHelper.instance.httpUrl).build()
    }


    fun <T> create (clazz: Class<T>): T {
        return getRetrofit().create(clazz)
    }

    /**
     * 规定请求超时时间
     */
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .build()


}