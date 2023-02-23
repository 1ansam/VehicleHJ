package com.yxf.vehiclehj.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehiclehj.bean.ExteriorItemR104Response
import com.yxf.vehiclehj.databinding.ItemExteriorItemBinding
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder

/**
 *   author:yxf
 *   time:2023/2/20
 */
class ExteriorItemRecyclerViewAdapter : BaseRvAdapter<ExteriorItemR104Response,ItemExteriorItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemExteriorItemBinding> {

        return BaseRvViewHolder(ItemExteriorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemExteriorItemBinding>,
        position: Int,
        binding: ItemExteriorItemBinding,
        bean: ExteriorItemR104Response,
    ) {
        binding.tvDm.text = bean.Dm
        binding.tvMc.text = bean.Mc
        binding.ivStatus.setOnClickListener {
            onItemViewClickListener?.onItemClick(it,position,bean)
        }
    }
}