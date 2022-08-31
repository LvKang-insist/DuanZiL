package com.dzl.duanzil.ui.video

import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragVideoBinding
import com.dzl.duanzil.viewmodel.video.VideoIntent
import com.dzl.duanzil.viewmodel.video.VideoViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import timber.log.Timber

/**
 * @name HomeFragment
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:36
 * @description 划一划
 */
class VideoFragment : BaseBindingFragment<FragVideoBinding>() {


    override fun getLayoutId(): Int = R.layout.frag_video

    override fun isStatusBarEnable(): Boolean = true

    override fun isStatusDarkFont(): Boolean = false

    override fun fitsSystemWindows(): Boolean = true

    override fun statusBarColor(): Int = R.color.black

    val adapter = VideoAdapter()
    val viewModel by viewModels<VideoViewModel>()

    override fun initView() {
        binding.page.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.page.adapter = adapter
        viewModel.dispatch(VideoIntent.LoadVideoList)
    }


    override fun listener() {
        viewModel.state.observe(this, Observer {
            it.list?.run {
                if (adapter.data.isEmpty()) {
                    adapter.addData(this)
                    playPosition(0)
                } else {
                    adapter.addData(this)
                }
            }
        })
        binding.page.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val playPosition = GSYVideoManager.instance().playPosition
                Timber.e("$playPosition       $position")
                //大于0表示有播放
                if (playPosition >= 0) {
                    //播放对应列表 TAG
                    if (GSYVideoManager.instance().playTag.equals(VideoAdapter.VideoAdapterHolder.TAG) && (position != playPosition)) {
                        GSYVideoManager.releaseAllVideos()
                        playPosition(position)
                    }
                }
            }
        })
    }


    private fun playPosition(position: Int) {
        binding.page.postDelayed({
            val viewHolder = (binding.page.getChildAt(0) as? RecyclerView)
                ?.findViewHolderForAdapterPosition(position)
            (viewHolder as? VideoAdapter.VideoAdapterHolder)?.run {
                getPlayer().startPlayLogic()
            }
        }, 50)
    }


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

}