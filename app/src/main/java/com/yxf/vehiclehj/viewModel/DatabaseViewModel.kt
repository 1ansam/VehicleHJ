package com.yxf.vehiclehj.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehiclehj.bean.SystemParamsR103Response
import com.yxf.vehiclehj.repo.DatabaseRepo
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2023/2/22
 */
class DatabaseViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {
    /**
     * 获取系统参数
     */
    fun getSystemParams() = flow { emit(databaseRepo.getSystemParams()) }
    /**
     * 插入系统参数
     * @param systemParamsList webapi获取到的系统参数列表
     * @return 返回插入的行数列表
     */
    suspend fun insertSystemParams(systemParamsList : List<SystemParamsR103Response>) : List<Long>{
        return databaseRepo.insertSystemParams(systemParamsList)
    }
    /**
     * 删除系统参数
     */
    suspend fun deleteSystemParams(){
        return databaseRepo.deleteSystemParams()
    }

    /**
     * 获取检验机构编号
     */
    fun getJyjgbh() = databaseRepo.getJyjgbh()
}

class DatabaseViewModelFactor(val databaseRepo: DatabaseRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DatabaseViewModel(databaseRepo) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}