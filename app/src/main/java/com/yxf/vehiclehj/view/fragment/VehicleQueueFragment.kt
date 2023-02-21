package com.yxf.vehiclehj.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.navigation.fragment.findNavController
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.bean.VehicleQueueR101Response
import com.yxf.vehiclehj.databinding.FragmentVehicleQueueBinding
import com.yxf.vehiclehj.databinding.FragmentVehicleQueueListBinding
import com.yxf.vehiclehj.utils.showShortToast
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter

/**
 * A fragment representing a list of Items.
 */
class VehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueListBinding>() {
    private val adapter = VehicleQueueRecyclerViewAdapter()
    override fun init() {
        binding.list.adapter = adapter
        adapter.data = listOf(
            VehicleQueueR101Response("苏12345","01"),
            VehicleQueueR101Response("苏22345","02"),
            VehicleQueueR101Response("苏32345","03"),
            VehicleQueueR101Response("苏42345","04"),
            VehicleQueueR101Response("苏52345","05"),
            VehicleQueueR101Response("苏62345","06"),
            VehicleQueueR101Response("苏72345","07"),
            VehicleQueueR101Response("苏82345","08"),
            VehicleQueueR101Response("苏92345","09"),
            VehicleQueueR101Response("苏102345","10"),
            VehicleQueueR101Response("苏112345","11"),
        )
        adapter.onItemViewClickListener = object : BaseRvAdapter.OnItemViewClickListener<VehicleQueueR101Response> {
            override fun onItemClick(view: View, position: Int, bean: VehicleQueueR101Response) {
                findNavController().navigate(VehicleQueueFragmentDirections.actionVehicleQueueFragmentToExteriorFragment())
            }

        }
        binding.sfRefresh.setOnRefreshListener {
            getQueue(binding.svHphm.query.toString())
            binding.sfRefresh.isRefreshing = false
        }
        binding.svHphm.apply {
            setOnClickListener {
                binding.svHphm.onActionViewExpanded()
            }
            setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    getQueue(query.toString())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }

    }

    private fun getQueue(hphm : String) {


    }

    override fun onResume() {
        super.onResume()
        getQueue(binding.svHphm.query.toString())
    }


}