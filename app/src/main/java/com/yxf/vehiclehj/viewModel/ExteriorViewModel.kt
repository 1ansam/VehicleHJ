package com.yxf.vehiclehj.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehiclehj.bean.ExteriorItemW101Request
import com.yxf.vehiclehj.bean.ExteriorPhotoR102Request
import com.yxf.vehiclehj.bean.SavePhotoW102Request
import com.yxf.vehiclehj.repo.ExteriorRepo
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2023/2/22
 */
class ExteriorViewModel(val exteriorRepo: ExteriorRepo) : ViewModel() {
    fun getExteriorPhoto(Hjlsh : String) = flow {
        emit(exteriorRepo.getExteriorPhoto(Hjlsh))
    }

    suspend fun saveExteriorItem( exteriorItemW101Request: ExteriorItemW101Request) = flow {
        emit(exteriorRepo.saveExteriorItem(exteriorItemW101Request))
    }

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