package com.yxf.vehiclehj.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yxf.vehiclehj.bean.SystemParamsR103Response
import kotlinx.coroutines.flow.Flow

/**
 *   author:yxf
 *   time:2021/11/11
 *   系统变量数据库操作类
 */
@Dao
interface SystemParamsDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR103Response>) : List<Long>

    @Update
    suspend fun updateSystemParams(systemParamsList: List<SystemParamsR103Response>)


    @Query("DELETE FROM SystemParams")
    suspend fun deleteSystemParams()
    @Query("SELECT Bz1 FROM SystemParams")
    fun getJyjgbh() : LiveData<String>

}