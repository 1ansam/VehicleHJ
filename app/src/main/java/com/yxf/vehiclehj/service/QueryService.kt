package com.yxf.vehicleinspection.service


import com.yxf.vehiclehj.bean.CommonResponse
import com.yxf.vehiclehj.bean.ExteriorPhotoR102Response
import com.yxf.vehiclehj.bean.SystemParamsR103Response
import com.yxf.vehiclehj.bean.VehicleQueueR101Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *   author:yxf
 *   time:2021/10/11
 *   retrofit公共查询请求接口
 */
interface QueryService {



    @POST("VehicleInspection/Query")
    suspend fun  queryR103(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String,
    ): CommonResponse<SystemParamsR103Response>
    /**
     * 根据号牌号码（可选的）单独查询环保机动车队列
     */
    @POST("VehicleInspection/Query")
    suspend fun  queryR101(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String,
    ): CommonResponse<VehicleQueueR101Response>

    /**
     * 查询机动车环检需要拍摄的照片
     */

    @POST("VehicleInspection/Query")
    suspend fun  queryR102(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String,
    ): CommonResponse<ExteriorPhotoR102Response>




}