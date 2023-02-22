package com.yxf.vehiclehj.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehiclehj.repo.QueueRepo
import com.yxf.vehiclehj.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2023/2/22
 */
class UserViewModel(val userRepo: UserRepo) : ViewModel() {

    fun writeUser(username : String, password : String) = flow {
        emit(userRepo.writeUser(username, password))
    }
}

class UserViewModelFactory(private val userRepo: UserRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepo) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }


}
