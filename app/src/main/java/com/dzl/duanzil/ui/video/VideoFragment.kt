package com.dzl.duanzil.ui.video

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragVideoBinding
import com.dzl.duanzil.viewmodel.video.VideoIntent
import com.dzl.duanzil.viewmodel.video.VideoViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager

/**
 * @name HomeFragment
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:36
 * @description εδΈε
 */
class VideoFragment : BaseBindingFragment<FragVideoBinding>() {


    override fun getLayoutId(): Int = R.layout.frag_video

    override fun isStatusBarEnable(): Boolean = true

    override fun isStatusDarkFont(): Boolean = false

    override fun fitsSystemWindows(): Boolean = true

    override fun statusBarColor(): Int = R.color.black

    val adapter by lazy { VideoAdapter(childFragmentManager, lifecycle) }
    val viewModel by viewModels<VideoViewModel>()

    override fun initView() {
        binding.page.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.page.adapter = adapter
        binding.page.offscreenPageLimit = 5
        viewModel.dispatch(VideoIntent.LoadVideoList)
    }


    override fun listener() {
        viewModel.state.observe(this, Observer {
            it.list?.run {
                val size = adapter.list.size
                adapter.list.addAll(this)
                adapter.notifyItemRangeChanged(size, adapter.list.size - size)
            }
        })
        binding.page.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val videoSize = adapter.list.size
                if (position > videoSize - 5) {
                    viewModel.dispatch(VideoIntent.LoadVideoList)
                }

//                val playPosition = GSYVideoManager.instance().playPosition
//                Timber.e("$playPosition       $position")
//                //ε€§δΊ0θ‘¨η€Ίζζ­ζΎ
//                if (playPosition >= 0) {
//                    //ζ­ζΎε―ΉεΊεθ‘¨ TAG
//                    if (GSYVideoManager.instance().playTag.equals(VideoAdapter.VideoAdapterHolder.TAG) && (position != playPosition)) {
//                        GSYVideoManager.releaseAllVideos()
//                        playPosition(position)
//                    }
//                }
            }
        })
    }


//    private fun playPosition(position: Int) {
//        binding.page.postDelayed({
//            val viewHolder = (binding.page.getChildAt(0) as? RecyclerView)
//                ?.findViewHolderForAdapterPosition(position)
//            (viewHolder as? VideoAdapter.VideoAdapterHolder)?.run {
//                getPlayer().startPlayLogic()
//            }
//        }, 50)
//    }


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