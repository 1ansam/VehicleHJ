package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2021/10/11
 *   查询接口返回数据类型
 *   @param Code 回复码
 *   @param Body 回复体
 *   @param RowNum 数据量
 *   @param Message 回复信息
 */
data class  CommonResponse<out T>(

    val Body: List<T>,
    val Code: String,
    val RowNum : Int,
    val Message: String
)


