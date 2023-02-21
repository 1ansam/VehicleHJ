package com.yxf.vehiclehj.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehiclehj.databinding.ItemExteriorItemBinding
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder

/**
 *   author:yxf
 *   time:2023/2/20
 */
class ExteriorItemRecyclerViewAdapter : BaseRvAdapter<Map.Entry<String,Boolean>,ItemExteriorItemBinding>() {
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
        bean: Map.Entry<String,Boolean>,
    ) {
        binding.tvName.text = bean.key
        binding.swValue.isChecked = bean.value
    }
}