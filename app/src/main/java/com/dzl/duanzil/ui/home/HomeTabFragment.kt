package com.dzl.duanzil.ui.home

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.core.other.AdapterHelper
import com.dzl.duanzil.core.other.GridSpaceItemDecoration
import com.dzl.duanzil.databinding.FragHomeTabBinding
import com.dzl.duanzil.state.ListStatus
import com.dzl.duanzil.state.home.HomeTabViewAction
import com.dzl.duanzil.ui.home.adapter.HomeTabAdapter
import com.dzl.duanzil.utils.JumpUtils
import com.dzl.duanzil.viewmodel.HomeTabViewModel
import com.hjq.toast.ToastUtils
import timber.log.Timber

/**
 * @name HomeTabFragment
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/03 16:21
 * @description
 */
class HomeTabFragment : BaseBindingFragment<FragHomeTabBinding>() {


    val viewModel by viewModels<HomeTabViewModel>()

    val type by lazy { arguments?.getInt("type") ?: 0 }

    var adapter = HomeTabAdapter()
    val adapterHelper by lazy {
        AdapterHelper(
            binding.refresh,
            isLoadMore = true,
            isRefresh = true,
            refresh = {
                viewModel.dispatch(HomeTabViewAction.RefreshList)
            },
            loadRefresh = {
                viewModel.dispatch(HomeTabViewAction.LoadMore(it.page))
            },
        )
    }

    override fun getLayoutId(): Int = R.layout.frag_home_tab

    override fun initView() {
        binding.recycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recycler.addItemDecoration(GridSpaceItemDecoration(20, true))
        binding.recycler.adapter = adapter
        lifecycleScope.launchWhenResumed {
            viewModel.dispatch(HomeTabViewAction.Type(type))
            adapterHelper.autoRefresh()
        }
    }

    var next: HomeTabFragment? = null
    fun testOOM() {
        val manager = requireContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        Timber.e("app maxMemory ${manager.memoryClass} Mb")
        Timber.e("large app maxMemory ${manager.largeMemoryClass} Mb")
        Timber.e("current app maxMemory ${Runtime.getRuntime().maxMemory() / 1024 / 1024} Mb")
        var count = 0
        val list = mutableListOf<ByteArray>()
        next = HomeTabFragment()
        Thread().apply {
            this.run {
                while (true) {
                    val h = HomeTabFragment()
                    next?.next = h
                    next = next?.next
//                    Timber.e("count $count    total ${count * 20}")
//                    list.add(ByteArray(1024 * 1024 * 20))
//                    Thread.sleep(500)

                    count++
                }
            }
            this.start()
        }
    }

    override fun listener() {
        adapter.addChildClickViewIds(R.id.like, R.id.like_count)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.like, R.id.like_count -> {
                    ToastUtils.show("点赞")
                }
            }
        }
        adapter.setOnItemClickListener { _, _, position ->
            testOOM()
            JumpUtils.startHomeDetail(requireContext(), adapter.data[position])
        }
    }

    override fun initData() {
        viewModel.state.observe(this) {
            when (it.state) {
                is ListStatus.RefreshSuccessStatus -> {
                    adapterHelper.run { adapter.refreshData(it.list) }
                }

                is ListStatus.LoadMoreSuccessStatus -> {
                    adapterHelper.run { adapter.loadData(it.list) }
                }
            }
        }
    }
}