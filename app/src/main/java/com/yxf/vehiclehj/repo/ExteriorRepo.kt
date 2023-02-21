package com.yxf.vehiclehj.repo

import com.yxf.vehiclehj.bean.ImageItemR102Response

/**
 *   author:yxf
 *   time:2023/2/20
 */
class ExteriorRepo {
    val inspectionGasItemList = mapOf(
        Pair("车辆机械状况是否良好",true),
        Pair("排气污染控制装置是否齐全，正常",true),
        Pair("车辆是否存在严重烧机油或者严重冒黑烟现象",false),
        Pair("曲轴箱通风系统是否正常",true),
        Pair("燃油蒸发控制系统是否正常",true),
        Pair("车上仪表工作是否正常",true),
        Pair("有无可能影响安全或引起测试偏差的机械故障",false),
        Pair("车辆进、排气系统是否有任何泄漏",true),
        Pair("车辆的发动机、变速箱和冷却系统等有无明显的液体渗漏",true),
        Pair("轮胎气压是否正常",true),
        Pair("轮胎是否干燥、清洁",true),
        Pair("是否关闭车上空调、暖风等附属设备",true),
        Pair("是否已经中断车辆上可能影响测试正常进行的功能，如 ARS、ESP、EPC 牵引力控制或自动制动系统等",true),
        Pair("车辆油箱和油品是否异常",true),
        Pair("是否适合工况法检测",true),
    )
    val inspectionDieselItemList = mapOf(
        Pair("车辆机械状况是否良好",true),
        Pair("排气污染控制装置是否齐全，正常",true),
        Pair("车辆是否存在严重烧机油或者严重冒黑烟现象",false),
        Pair("曲轴箱通风系统是否正常",true),
        Pair("燃油蒸发控制系统是否正常",true),
        Pair("车上仪表工作是否正常",true),
        Pair("有无可能影响安全或引起测试偏差的机械故障",false),
        Pair("车辆进、排气系统是否有任何泄漏",true),
        Pair("车辆的发动机、变速箱和冷却系统等有无明显的液体渗漏",true),
        Pair("是否带 OBD 系统",true),
        Pair("轮胎气压是否正常",true),
        Pair("轮胎是否干燥、清洁",true),
        Pair("是否关闭车上空调、暖风等附属设备",true),
        Pair("是否已经中断车辆上可能影响测试正常进行的功能，如 ARS、ESP、EPC 牵引力控制或自动制动系统等",true),
        Pair("车辆油箱和油品是否异常",true),
        Pair("是否适合工况法检测",true),
    )

    val photoList = listOf<ImageItemR102Response>(
        ImageItemR102Response("100201","前45度"),
        ImageItemR102Response("100202","后45度"),
        ImageItemR102Response("100203","车架号"),
        ImageItemR102Response("100204","发动机号"),
        ImageItemR102Response("100205","仪表盘里程")
    )
}