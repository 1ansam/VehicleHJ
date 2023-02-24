package com.yxf.vehiclehj.view.fragment

import android.util.Log
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.yxf.vehiclehj.MyApp
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.databinding.FragmentLoginBinding
import com.yxf.vehiclehj.utils.showShortSnackbar
import com.yxf.vehiclehj.viewModel.UserViewModel
import com.yxf.vehiclehj.viewModel.UserViewModelFactory
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.singleton.SharedP
import kotlinx.coroutines.flow.collect


class LoginFragment : BaseBindingFragment<FragmentLoginBinding>() {
    private val viewModel by viewModels<UserViewModel> { UserViewModelFactory((this.requireActivity().application as MyApp).userRepo) }
    override fun init() {
        if (SharedP.instance.getString("username","")!=null && SharedP.instance.getString("username","")!= ""){
            binding.cbRememberUsername.isChecked = true
        }
        binding.tvUsername.apply {
            setText(SharedP.instance.getString("username", ""))
        }
        binding.btnLogin.setOnClickListener {
            if (binding.cbRememberUsername.isChecked) {
                SharedP.instance.edit{
                    putString("username", binding.tvUsername.text.toString())
                }

            }else{
                SharedP.instance.edit{
                    putString("username","")
                }

            }
            login(binding.tvUsername.text.toString(),binding.tvPassword.text.toString())
        }


        binding.btnSettings.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSettingFragment())

        }
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    fun login(username : String, password : String){
        lifecycleScope.launchWhenCreated {
            viewModel.writeUser(username,password).collect{
                if (it.Code != "1"){
                    showShortSnackbar(binding.root,it.Message)
                    Log.e("TAG", "login: ${it.Message}", )
                    return@collect
                }
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToVehicleQueueFragment(it.Body.first()))
            }


        }

    }

}