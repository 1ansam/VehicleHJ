package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2023/2/22
 */
data class UserInfoW001Response(
    val ID : String,
    val GongHao : String,
    val UserName : String,
    val PassWord : String,
    val TrueName : String,
    val AddDate : String,
    val RoleDm : String
) : java.io.Serializable