package com.yxf.vehiclehj.repo

import com.yxf.vehiclehj.bean.UserInfoW001Request
import com.yxf.vehiclehj.utils.WRITE_USER_LOGIN
import com.yxf.vehiclehj.utils.apiCall
import com.yxf.vehiclehj.utils.getIpAddress
import com.yxf.vehiclehj.utils.getJsonData
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.RetrofitService

/**
 *   author:yxf
 *   time:2023/2/22
 */
class UserRepo {
    suspend fun writeUser(username: String, password: String) = apiCall {
        RetrofitService.create(WriteService::class.java).writeW001(
            WRITE_USER_LOGIN,
            getIpAddress(),
            getJsonData(UserInfoW001Request(username, password, getIpAddress()))
        )
    }
}