package com.yxf.vehiclehj.repo

import androidx.lifecycle.LiveData
import com.yxf.vehiclehj.bean.SystemParamsR103Request
import com.yxf.vehiclehj.bean.SystemParamsR103Response
import com.yxf.vehiclehj.room.SystemParamsDao
import com.yxf.vehiclehj.utils.*
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService
import kotlinx.coroutines.flow.Flow

/**
 *   author:yxf
 *   time:2023/2/22
 */
class DatabaseRepo(private val dao : SystemParamsDao) {
    suspend fun getSystemParams() = apiCall {
        RetrofitService.create(QueryService::class.java).queryR103(
            SYSTEM_PARAMS,
            getIpAddress(),
            getJsonData(SystemParamsR103Request())
        )
    }
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR103Response>) : List<Long>{
        return dao.insertSystemParams(systemParamsList)
    }

    suspend fun deleteSystemParams(){
        dao.deleteSystemParams()
    }

    fun getJyjgbh() : LiveData<String> {
        return dao.getJyjgbh()
    }
}