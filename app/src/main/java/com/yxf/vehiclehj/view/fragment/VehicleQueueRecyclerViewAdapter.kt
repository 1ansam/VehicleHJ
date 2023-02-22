package com.yxf.vehiclehj.view.fragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.bean.VehicleQueueR101Response
import com.yxf.vehiclehj.databinding.FragmentVehicleQueueBinding
import com.yxf.vehiclehj.databinding.FragmentVehicleQueueListBinding
import com.yxf.vehiclehj.utils.showShortToast

import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import java.util.zip.Inflater

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class VehicleQueueRecyclerViewAdapter() :  BaseRvAdapter<VehicleQueueR101Response,FragmentVehicleQueueBinding>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<FragmentVehicleQueueBinding> {
        val binding = FragmentVehicleQueueBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRvViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<FragmentVehicleQueueBinding>,
        position: Int,
        binding: FragmentVehicleQueueBinding,
        bean: VehicleQueueR101Response,
    ) {
        binding.itemNumber.text = bean.Hjlsh
        binding.content.text = bean.Hphm
        binding.tvDjrq.text = bean.Djrq
        holder.itemView.setOnClickListener{
            onItemViewClickListener?.onItemClick(it,position,bean)
        }

    }







}