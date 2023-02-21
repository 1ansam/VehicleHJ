package com.yxf.vehiclehj.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehiclehj.bean.ImageItemR102Response
import com.yxf.vehiclehj.databinding.ItemExteriorPhotoBinding
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder

/**
 *   author:yxf
 *   time:2023/2/21
 */
class ExteroirPhotoRecyclerViewAdapter :
    BaseRvAdapter<ImageItemR102Response, ItemExteriorPhotoBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemExteriorPhotoBinding> {
        return BaseRvViewHolder(ItemExteriorPhotoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemExteriorPhotoBinding>,
        position: Int,
        binding: ItemExteriorPhotoBinding,
        bean: ImageItemR102Response,
    ) {
        binding.tvZpmc.text = bean.Zpmc
        binding.tvZpdm.text = bean.Zpdm
        binding.ivTakePhoto.setOnClickListener {
            onItemViewClickListener?.onItemClick(it,position,bean)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}