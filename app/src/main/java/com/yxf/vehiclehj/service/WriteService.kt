package com.yxf.vehicleinspection.service


import com.yxf.vehiclehj.bean.CommonResponse
import com.yxf.vehiclehj.bean.ExteriorItemW101Request
import com.yxf.vehiclehj.bean.SavePhotoW102Response
import com.yxf.vehiclehj.bean.UserInfoW001Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *   author:yxf
 *   time:2021/10/11
 *   retrofit公共写入接口
 */
interface WriteService {
    /**
     * 写入用户登录
     *   @param jkId 接口标识
     *   @param zdbs 终端标识
     *   @param jsonData json数据
     *   @return 返回通用响应体
     */


    @POST("VehicleInspection/Write")
    suspend fun  writeW001(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Body jsonData: String,
    ): CommonResponse<UserInfoW001Response>
    /**
     * 保存外检项目
     */
    @POST("VehicleInspection/Write")
    suspend fun  writeW101(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Body jsonData: String,
    ): CommonResponse<ExteriorItemW101Request>

    /**
     * 保存外检照片
     */
    @POST("VehicleInspection/Write")
    suspend fun  writeW102(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Body jsonData: String,
    ): CommonResponse<SavePhotoW102Response>



}