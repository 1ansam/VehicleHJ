package com.yxf.vehiclehj.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehiclehj.bean.ExteriorPhotoR102Response
import com.yxf.vehiclehj.databinding.ItemExteriorPhotoBinding
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder

/**
 *   author:yxf
 *   time:2023/2/21
 */
class ExteroirPhotoRecyclerViewAdapter :
    BaseRvAdapter<ExteriorPhotoR102Response, ItemExteriorPhotoBinding>() {
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
        bean: ExteriorPhotoR102Response,
    ) {
        binding.tvZpmc.text = bean.Zpmc
        binding.tvZpdm.text = bean.Zpdm
        binding.ivTakePhoto.setOnClickListener {
            onItemViewClickListener?.onItemClick(it,position,bean)
        }
    }

    /**
     * 防止ViewHolder错误复用 最简单最笨的办法 后续需改进
     */
    override fun getItemViewType(position: Int): Int {
        return position
    }
}