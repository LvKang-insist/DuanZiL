package com.dzl.duanzil.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.core.vp.IndicatorNavAdapter
import com.dzl.duanzil.core.vp.ViewPager2Helper
import com.dzl.duanzil.databinding.FragHomeBinding
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * @name HomeFragment
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:36
 * @description
 */
class HomeFragment : BaseBindingFragment<FragHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.frag_home

    override fun isStatusBarEnable(): Boolean = true

    override fun getTitleBar(): View = binding.indicator

    private val tabList = arrayListOf("推荐", "趣图", "最新")


    override fun initView() {
        initTabIndicator()
    }

    private fun initTabIndicator() {
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.isAdjustMode = false
        commonNavigator.adapter = IndicatorNavAdapter(tabList, textSize = 18f, onClick = {
            binding.viewpager.currentItem = it
        })
        binding.indicator.navigator = commonNavigator
        ViewPager2Helper.bind(binding.indicator, binding.viewpager)

        setTab()
    }

    private fun setTab() {
        binding.viewpager.adapter =
            object : FragmentStateAdapter(childFragmentManager, lifecycle) {
                override fun getItemCount(): Int = tabList.size
                override fun createFragment(position: Int): Fragment {
                    val bundle = Bundle()
                    bundle.putInt("type", position)
                    val fragment = HomeTabFragment()
                    fragment.arguments = bundle
                    return when (position) {
                        0 -> fragment
                        1 -> fragment
                        else -> Fragment()
                    }
                }
            }
    }


}