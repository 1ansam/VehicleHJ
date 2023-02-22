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
    fun getSystemParams() = flow { emit(databaseRepo.getSystemParams()) }
    suspend fun insertSystemParams(systemParamsList : List<SystemParamsR103Response>) : List<Long>{
        return databaseRepo.insertSystemParams(systemParamsList)
    }
    suspend fun deleteSystemParams(){
        return databaseRepo.deleteSystemParams()
    }

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