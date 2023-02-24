package com.yxf.vehiclehj.repo

import com.yxf.vehiclehj.bean.*
import com.yxf.vehiclehj.utils.*
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.RetrofitService

/**
 *   author:yxf
 *   time:2023/2/20
 *   外观检验Repository
 */
class ExteriorRepo {
    /**
     * 获取外检项目
     * @param lsh 流水号
     */
    suspend fun getExteriorItem(lsh : String) = apiCall {
        RetrofitService.create(QueryService::class.java).queryR104(
            EXTERIOR_ITEM,
            getIpAddress(),
            getJsonData(ExteriorItemR104Request(lsh))
        )
    }

    /**
     * 获取外检照片
     * @param lsh 流水号
     */

    suspend fun getExteriorPhoto(
        lsh: String,
    ) = apiCall {
        RetrofitService.create(QueryService::class.java).queryR102(
            EXTERIOR_PHOTO,
            getIpAddress(),
            getJsonData(ExteriorPhotoR102Request(lsh))
        )
    }

    /**
     * 保存外检项目
     * @param exteriorItemW101Request 外检项目
     */
    suspend fun saveExteriorItem( exteriorItemW101Request: ExteriorItemW101Request) = apiCall {
        RetrofitService.create(WriteService::class.java).writeW101(
            SAVE_EXTERIOR_ITEM,
            getIpAddress(),
            getJsonData(exteriorItemW101Request)
        )
    }


    /**
     * 保存外检照片
     * @param exteriorPhotos 外检照片列表
     */
    suspend fun saveExteriorPhoto(exteriorPhotos : List<SavePhotoW102Request>) = apiCall {
        RetrofitService.create(WriteService::class.java).writeW102(
            SAVE_EXTERIOR_PHOTO,
            getIpAddress(),
            getJsonData(exteriorPhotos)
        )
    }
}