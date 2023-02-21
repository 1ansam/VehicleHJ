package com.yxf.vehicleinspection.service


import com.yxf.vehiclehj.bean.CommonResponse
import com.yxf.vehiclehj.bean.VehicleQueueR101Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *   author:yxf
 *   time:2021/10/11
 *   retrofit公共查询请求接口
 */
interface QueryService {



    @POST("VehicleInspection/Query")
    suspend fun  queryR026(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String,
    ): CommonResponse<VehicleQueueR101Response>




}