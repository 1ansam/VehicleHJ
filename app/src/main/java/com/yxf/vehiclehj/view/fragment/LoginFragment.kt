package com.yxf.vehiclehj.view.fragment

import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.databinding.FragmentLoginBinding
import com.yxf.vehicleinspection.base.BaseBindingFragment


class LoginFragment : BaseBindingFragment<FragmentLoginBinding>() {
    override fun init() {
        binding.btnLogin.setOnClickListener {
            login(binding.tvUsername.text.toString(),binding.tvPassword.text.toString())
        }


        binding.btnSettings.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSettingFragment())

        }
    }

    fun login(username : String, password : String){
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToVehicleQueueFragment())
    }

}