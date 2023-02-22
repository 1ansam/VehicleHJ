package com.yxf.vehiclehj.repo

import com.yxf.vehiclehj.bean.VehicleQueueR101Request
import com.yxf.vehiclehj.utils.VEHICLE_QUEUE
import com.yxf.vehiclehj.utils.apiCall
import com.yxf.vehiclehj.utils.getIpAddress
import com.yxf.vehiclehj.utils.getJsonData
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService

/**
 *   author:yxf
 *   time:2023/2/22
 */
class QueueRepo {
    /**
     * 获取机动车待检队列
     * @param hphm 号牌号码
     */
    suspend fun getInspectionQueue(hphm: String) = apiCall {
        RetrofitService.create(QueryService::class.java).queryR101(
            VEHICLE_QUEUE,
            getIpAddress(),
            getJsonData(VehicleQueueR101Request(hphm))
        )
    }
}