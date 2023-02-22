package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2021/10/11
 *   @param UserName 用户名
 *   @param PassWord 密码
 *   @param IP IP地址
 */
data class UserInfoW001Request(
    val UserName: String,
    val PassWord: String,
    val IP: String
)