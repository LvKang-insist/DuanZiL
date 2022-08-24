package com.dzl.duanzil.ui.release

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingActivity
import com.dzl.duanzil.core.vp.IndicatorNavAdapter
import com.dzl.duanzil.core.vp.ViewPager2Helper
import com.dzl.duanzil.databinding.ActivityReleaseBinding
import com.dzl.duanzil.ui.release.tab.CameraImageFragment
import com.dzl.duanzil.ui.release.tab.CameraVideoFragment
import com.dzl.duanzil.ui.release.tab.SelectPhotoFragment
import com.gyf.immersionbar.ktx.immersionBar
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * @name ReleaseActivity
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:36
 * @description 发布
 */

class ReleaseActivity : BaseBindingActivity<ActivityReleaseBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_release

    override fun isStatusBarEnable(): Boolean = false

    private var videoFragment: CameraVideoFragment? = null
    private var imageFragment: CameraImageFragment? = null

    override fun initView() {
        initBar()
        initTabIndicator()
    }

    private fun initBar() {
        immersionBar {
            statusBarColor(R.color.transparent)
            statusBarDarkFont(false)
        }
    }

    fun initTabIndicator() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter =
            IndicatorNavAdapter(
                arrayListOf("相册", "拍视频", "拍照"),
                color = R.color.skin_primary,
                unColor = R.color.textWhiteColor,
                onClick = {
                    binding.viewpager.currentItem = it
                })
        binding.indicator.navigator = commonNavigator
        ViewPager2Helper.bind(binding.indicator, binding.viewpager)
        setTab()
    }


    private fun setTab() {
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int = 3

                override fun createFragment(position: Int): Fragment = when (position) {
                    0 -> SelectPhotoFragment()
                    1 -> {
                        videoFragment = CameraVideoFragment()
                        videoFragment!!
                    }
                    else -> {
                        imageFragment = CameraImageFragment()
                        imageFragment!!
                    }
                }
            }
    }

}