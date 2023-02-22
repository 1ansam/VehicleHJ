package com.yxf.vehiclehj.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yxf.vehiclehj.MyApp
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.bean.VehicleQueueR101Response
import com.yxf.vehiclehj.databinding.FragmentVehicleQueueBinding
import com.yxf.vehiclehj.databinding.FragmentVehicleQueueListBinding
import com.yxf.vehiclehj.utils.showShortSnackbar
import com.yxf.vehiclehj.utils.showShortToast
import com.yxf.vehiclehj.viewModel.QueueViewModel
import com.yxf.vehiclehj.viewModel.QueueViewModelFactory
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter

/**
 * A fragment representing a list of Items.
 */
class VehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueListBinding>() {
    private val viewModel by viewModels<QueueViewModel>{ QueueViewModelFactory((this.requireActivity().application as MyApp).queueRepo)}
    private val adapter = VehicleQueueRecyclerViewAdapter()
    override fun init() {
        binding.list.adapter = adapter
        adapter.data = listOf(
        )
        adapter.onItemViewClickListener = object : BaseRvAdapter.OnItemViewClickListener<VehicleQueueR101Response> {
            override fun onItemClick(view: View, position: Int, bean: VehicleQueueR101Response) {
                findNavController().navigate(VehicleQueueFragmentDirections.actionVehicleQueueFragmentToExteriorFragment(bean))
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
        lifecycleScope.launchWhenCreated {
            viewModel.getInspectionQueue(hphm).collect{
                if (it.Code != "1"){
                    Log.e("TAG", "getQueue: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }
                adapter.data = it.Body
            }
        }


    }

    override fun onResume() {
        super.onResume()
        getQueue(binding.svHphm.query.toString())
    }


}