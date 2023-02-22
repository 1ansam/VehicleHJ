package com.yxf.vehiclehj.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yxf.vehiclehj.MyApp
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.databinding.FragmentSettingBinding
import com.yxf.vehiclehj.utils.showShortSnackbar
import com.yxf.vehiclehj.view.activity.MainActivity
import com.yxf.vehiclehj.viewModel.DatabaseViewModel
import com.yxf.vehiclehj.viewModel.DatabaseViewModelFactor
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.singleton.SharedP
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect


class SettingFragment : BaseBindingFragment<FragmentSettingBinding>() {
    private val viewModel by viewModels<DatabaseViewModel> { DatabaseViewModelFactor((this.requireActivity().application as MyApp).databaseRepo) }
    override fun init() {
        binding.tvIpAddress.setText(SharedP.instance.getString("ipAddress", "192.168.1.1"))
        binding.tvPort.setText(SharedP.instance.getString("ipPort", "80"))
        binding.btnSync.setOnClickListener{
            SharedP.instance.edit {
                putString("ipAddress", binding.tvIpAddress.text.toString())
                putString("ipPort", binding.tvPort.text.toString())
            }


            BaseUrlHelper.instance.setHostField(binding.tvIpAddress.text.toString())
            BaseUrlHelper.instance.setPortField(binding.tvPort.text.toString().toInt())
            getData()


        }
    }

    private fun getData() {

        lifecycleScope.launchWhenCreated {
            viewModel.deleteSystemParams()
            viewModel.getSystemParams().collect{
                if (it.Code!="1"){
                    Log.e("TAG", "getData: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }

                viewModel.insertSystemParams(it.Body)
                findNavController().popBackStack()

            }
        }

    }

}