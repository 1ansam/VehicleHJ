package com.yxf.vehiclehj.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehiclehj.repo.ExteriorRepo
import com.yxf.vehiclehj.repo.QueueRepo
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2023/2/22
 */
class QueueViewModel(val queueRepo: QueueRepo) : ViewModel() {
    /**
     * 获取机动车待检队列
     * @param hphm 号牌号码
     */
    fun getInspectionQueue(hphm : String) = flow{
        emit(queueRepo.getInspectionQueue(hphm))
    }
}

class QueueViewModelFactory(private val queueRepo: QueueRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QueueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QueueViewModel(queueRepo) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }


}