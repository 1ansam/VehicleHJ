package com.yxf.vehiclehj.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   author:yxf
 *   time:2021/11/11
 *   系统参数 与 SystermCs_All 对应
 *   @param Bz1 检验机构编号
 */
@Entity(tableName = "SystemParams")
data class SystemParamsR103Response(
    @PrimaryKey val JcDateYouXiao : String,
    val PrintAll  : String?,
    val PrintAuto : String?,
    val NetRound  : String?,
    val BsOK        : String?,
    val BsNo        : String?,
    val BsDefalue   : String?,
    val BsWx        : String?,
    val ShowUp      : String?,
    val ShowDown    : String?,
    val Web_Udl     : String?,
    val WEB_Work    : String?,
    val Web_Pass    : String?,
    val Web_Cjdw    : String?,
    val WEB_Pic     : String?,
    val WEB_Menoy   : String?,
    val Dw_Xkzh     : String?,
    val Dw_Dhhm     : String?,
    val Dw_sqzzr    : String?,
    val Report_Head : String?,
    val Report_BZ : String?,
    val Bz1 : String?,
    val Bz2 : String?,
    val Bz3 : String?,
    val Bz4 : String?,
    val Bz5 : String?,
    val Bz6 : String?,
    val Bz7 : String?,
    val Bz8 : String?,
    val Bz9 : String?,
    val Bz10 : String?,

    )