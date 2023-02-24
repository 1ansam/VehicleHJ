package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2023/2/22
 *   @param Hjlsh 环检流水号
 *   @param Jyxm 检验项目 默认值 F1
 */
data class ExteriorPhotoR102Request(
    val Hjlsh : String,
    val Jyxm : String = "F1"

) {
}