package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2023/2/22
 *   人员信息
 *   @param ID 身份证号
 *   @param GongHao 工号
 *   @param UserName 用户名
 *   @param PassWord 密码
 *   @param TrueName 真实姓名
 *   @param AddDate 添加日期
 *   @param RoleDm 权限代码
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