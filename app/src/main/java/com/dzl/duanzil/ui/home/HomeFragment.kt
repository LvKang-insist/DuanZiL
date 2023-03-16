package com.dzl.duanzil.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.core.vp.IndicatorNavAdapter
import com.dzl.duanzil.core.vp.ViewPager2Helper
import com.dzl.duanzil.databinding.FragHomeBinding
import com.dzl.duanzil.utils.GlideUtil
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer
import com.youth.banner.transformer.DepthPageTransformer
import com.youth.banner.transformer.ScaleInTransformer
import com.youth.banner.transformer.ZoomOutPageTransformer
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

    override fun getTitleBar(): View = binding.headLayout

    private val tabList = arrayListOf("推荐", "最新", "趣图", "纯文")


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

        setBanner()
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
                        2 -> fragment
                        3 -> fragment
                        else -> Fragment()
                    }
                }
            }
    }


    private fun setBanner() {
        val list = mutableListOf<String>()
        list.add("https://img0.baidu.com/it/u=1677462811,726287569&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500")
        list.add("https://img0.baidu.com/it/u=3496194910,739642242&fm=253&fmt=auto&app=120&f=JPEG?w=1200&h=800")
        list.add("https://img1.baidu.com/it/u=648682337,227066329&fm=253&fmt=auto?w=800&h=500")
        list.add("https://img2.baidu.com/it/u=516577964,3587447629&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500")
        binding.banner.apply {
            addPageTransformer(ScaleInTransformer())
            addPageTransformer(AlphaPageTransformer())
            setAdapter(object : BannerImageAdapter<String>(list) {
                override fun onBindView(
                    holder: BannerImageHolder?, data: String?, position: Int, size: Int
                ) {
                    holder?.run {
//                        Glide.with(holder.imageView).load(data).into(holder.imageView)
                        GlideUtil.loadRoundImage(binding.banner.context, data, holder.imageView,8)
                    }
                }

            })
            addBannerLifecycleObserver(viewLifecycleOwner)
            setIndicator(CircleIndicator(requireContext()))
        }
    }

}