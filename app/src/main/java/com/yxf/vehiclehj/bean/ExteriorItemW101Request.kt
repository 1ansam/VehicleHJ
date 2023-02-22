package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2023/2/21
 *   @param Lsh 流水号
 *   @param Hpzl 号牌种类
 *   @param Hphm 号牌号码
 *   @param Jccs 检测次数
 *   @param JcData 检测日期
 *   @param KsTime 检测开始时间
 *   @param JsTime 检测结束时间
 *   @param Jcpj 检测评价
 *   @param DaLB 检验项目
 *   @param WorkJcxm 不合格项目
 *   @param WorkMan 检测员
 */
data class ExteriorItemW101Request(
    val Lsh : String,
    val Hpzl : String,
    val Hphm : String,
    val Jccs : Int,
    val JcData : String,
    val KsTime : String,
    val JsTime : String,
    val Jcpj : String,
    val DaLB : String,
    val WorkJcxm : String,
    val WorkMan : String
) {
}