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
 *   数据字典repository
 *   @param dao SystemParams数据库操作类
 */
class DatabaseRepo(private val dao : SystemParamsDao) {
    /**
     * 获取系统参数
     */
    suspend fun getSystemParams() = apiCall {
        RetrofitService.create(QueryService::class.java).queryR103(
            SYSTEM_PARAMS,
            getIpAddress(),
            getJsonData(SystemParamsR103Request())
        )
    }

    /**
     * 插入系统参数
     * @param systemParamsList webapi获取到的系统参数列表
     * @return 返回插入的行数列表
     */
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR103Response>) : List<Long>{
        return dao.insertSystemParams(systemParamsList)
    }

    /**
     * 删除系统参数
     */
    suspend fun deleteSystemParams(){
        dao.deleteSystemParams()
    }

    /**
     * 获取检验机构编号
     */
    fun getJyjgbh() : LiveData<String> {
        return dao.getJyjgbh()
    }
}