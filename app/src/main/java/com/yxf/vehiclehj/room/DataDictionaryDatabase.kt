package com.yxf.vehiclehj.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yxf.vehiclehj.bean.SystemParamsR103Response
/**
 *   author:yxf
 *   time:2021/11/9
 *   数据库创建类
 *   详细信息见官方room文档
 */
@Database(entities = [SystemParamsR103Response::class],version = 2,exportSchema = false)
abstract class DataDictionaryDatabase : RoomDatabase() {
    abstract fun systemParamsDao() : SystemParamsDao
    companion object {
        @Volatile
        private var INSTANCE: DataDictionaryDatabase? = null

        fun getDatabase(
            context: Context,
        ): DataDictionaryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataDictionaryDatabase::class.java,
                    "Database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}