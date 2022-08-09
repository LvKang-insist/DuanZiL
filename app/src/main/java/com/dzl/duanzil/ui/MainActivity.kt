package com.dzl.duanzil.ui

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingActivity
import com.dzl.duanzil.databinding.ActivityMainBinding
import com.dzl.duanzil.ui.adapter.MainNavigationAdapter
import com.dzl.duanzil.ui.adapter.MainNavigationItemClickListener
import com.dzl.duanzil.ui.home.HomeFragment
import com.dzl.duanzil.ui.msg.MsgFragment
import com.dzl.duanzil.ui.release.ReleaseFragment
import com.dzl.duanzil.ui.user.UserFragment
import com.dzl.duanzil.ui.video.VideoFragment

class MainActivity : BaseBindingActivity<ActivityMainBinding>(), MainNavigationItemClickListener {

    private val list = arrayListOf<MainNavigationAdapter.MenuItem>()
    private val navigationAdapter by lazy { MainNavigationAdapter(list) }
    private val pageAdapter by lazy { PageAdapter(supportFragmentManager, lifecycle, list) }

    override fun getLayoutId(): Int = R.layout.activity_main


    override fun initView() {
        navigationAdapter.listener = this
        binding.mainNavigation.layoutManager = GridLayoutManager(this, 5)
    }


    override fun initData() {
        list.add(
            MainNavigationAdapter.MenuItem(
                "首页", ContextCompat.getDrawable(this, R.drawable.main_home_selector)!!,
            )
        )
        list.add(
            MainNavigationAdapter.MenuItem(
                "划一划", ContextCompat.getDrawable(this, R.drawable.main_slide_selector)!!,
            )
        )
        list.add(
            MainNavigationAdapter.MenuItem(
                "发布", ContextCompat.getDrawable(this, R.drawable.main_release_ic)!!,
            )
        )
        list.add(
            MainNavigationAdapter.MenuItem(
                "消息", ContextCompat.getDrawable(this, R.drawable.main_msg_selector)!!,
            )
        )
        list.add(
            MainNavigationAdapter.MenuItem(
                "我的", ContextCompat.getDrawable(this, R.drawable.main_user_selector)!!,
            )
        )
        binding.mainNavigation.adapter = navigationAdapter
        binding.mainViewpager.adapter = pageAdapter
        binding.mainViewpager.isUserInputEnabled = false
        binding.mainViewpager.offscreenPageLimit = list.size
    }

    override fun onClick(position: Int) {
        binding.mainViewpager.setCurrentItem(position,false)
    }

    class PageAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        private val list: List<Any>
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return HomeFragment()
                1 -> return VideoFragment()
                2 -> return ReleaseFragment()
                3 -> return MsgFragment()
                4 -> return UserFragment()
            }
            return Fragment()
        }

    }
}