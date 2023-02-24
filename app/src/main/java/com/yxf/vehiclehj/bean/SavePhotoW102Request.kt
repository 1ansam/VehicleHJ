package com.yxf.vehiclehj.bean

/**
 *   author:yxf
 *   time:2023/2/22
 *   上传照片信息
 *   与表UpLoad_Wg_Pic对应
 *   @param Jylsh 检验流水号
 *   @param Jyjgbh 检验机构编号
 *   @param Jcxdh 检测线代号
 *   @param Jycs 检验次数
 *   @param Hphm 号牌号码
 *   @param Hpzl 号牌种类
 *   @param Clsbdh 车辆识别代号
 *   @param Zp 照片Base64
 *   @param Pssj 拍摄时间 yyyyMMddHHmmss
 *   @param Jyxm 检验项目F1
 *   @param Zpzl 照片代码
 *   @param Zpmc 照片名称
 *   @param BzO2 环检登录时间 yyyyMMddHHmmss
 */
data class SavePhotoW102Request(
    val Jylsh : String,
    val Jyjgbh : String,
    val Jcxdh : String,
    val Jycs : Int,
    val Hphm : String,
    val Hpzl : String,
    val Clsbdh : String,
    val Zp : String,
    val Pssj : String,
    val Jyxm : String,
    val Zpzl : String,
    val Zpmc : String,
    val BzO2 : String,
) {
}