package com.ko2ic.coroutinesflow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ko2ic.coroutinesflow.R
import com.ko2ic.coroutinesflow.databinding.FragmentHomeBinding
import com.ko2ic.coroutinesflow.ui.adapter.ItemViewTypeProvider
import com.ko2ic.coroutinesflow.ui.adapter.RecyclerViewAdapter
import com.ko2ic.coroutinesflow.ui.viewmodel.CollectionItemViewModel
import com.ko2ic.coroutinesflow.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )

        binding.viewModel = homeViewModel

        val itemViewTypeProvider = object : ItemViewTypeProvider {
            override fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int {
                // TODO: ここでViewModelを見てデザインを変える
                return R.layout.adapter_item
            }
        }

        binding.recyclerView.adapter =
            RecyclerViewAdapter(homeViewModel.viewModels, itemViewTypeProvider)

        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.create()
    }
}