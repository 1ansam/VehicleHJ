package com.yxf.vehiclehj.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehiclehj.bean.CommonResponse
import com.yxf.vehiclehj.bean.ExteriorItemR104Response
import com.yxf.vehiclehj.bean.ExteriorItemW101Request
import com.yxf.vehiclehj.bean.SavePhotoW102Request
import com.yxf.vehiclehj.repo.ExteriorRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2023/2/22
 */
class ExteriorViewModel(val exteriorRepo: ExteriorRepo) : ViewModel() {
    /**
     * 获取外检项目
     * @param lsh 流水号
     */
    fun exteriorItem(lsh : String) = flow { emit(exteriorRepo.getExteriorItem(lsh)) }
    /**
     * 获取外检照片
     * @param lsh 流水号
     */
    fun getExteriorPhoto(lsh : String) = flow {
        emit(exteriorRepo.getExteriorPhoto(lsh))
    }


    /**
     * 保存外检项目
     * @param exteriorItemW101Request 外检项目
     */
    fun saveExteriorItem( exteriorItemW101Request: ExteriorItemW101Request) = flow {
        emit(exteriorRepo.saveExteriorItem(exteriorItemW101Request))
    }
    /**
     * 保存外检照片
     * @param exteriorPhotos 外检照片列表
     */
    fun saveExteriorPhoto(exteriorPhotos : List<SavePhotoW102Request>) = flow {
        emit(exteriorRepo.saveExteriorPhoto(exteriorPhotos))
    }


}

class ExteriorViewModelFactory(private val exteriorRepo: ExteriorRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExteriorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExteriorViewModel(exteriorRepo) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }


}