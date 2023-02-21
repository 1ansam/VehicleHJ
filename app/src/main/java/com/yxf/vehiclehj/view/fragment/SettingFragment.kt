package com.yxf.vehiclehj.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.databinding.FragmentSettingBinding
import com.yxf.vehiclehj.view.activity.MainActivity
import com.yxf.vehicleinspection.base.BaseBindingFragment


class SettingFragment : BaseBindingFragment<FragmentSettingBinding>() {
    override fun init() {
        binding.btnSync.setOnClickListener{

            findNavController().popBackStack()
        }
    }

}