package com.dzl.duanzil.ui.video

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragVideoBinding
import com.dzl.duanzil.extension.removeViewFormParent
import com.dzl.duanzil.ui.dialog.CommentDialogFragment
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.cache.PreloadManager
import com.dzl.duanzil.viewmodel.video.VideoIntent
import com.dzl.duanzil.viewmodel.video.VideoViewModel
import com.dzl.duanzil.widgets.controller.TikTokController
import com.dzl.duanzil.widgets.render.TikTokRenderViewFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber
import xyz.doikki.videoplayer.player.VideoView

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

    val adapter by lazy { VideoAdapter() }
    val viewModel by viewModels<VideoViewModel>()

    val preloadManager: PreloadManager by lazy { PreloadManager.getInstance(requireContext()) }
    val controller by lazy { TikTokController(requireContext()) }
    val videoView by lazy { VideoView(requireContext()) }
    val viewPagerImpl by lazy { binding.page.getChildAt(0) as RecyclerView }
    val commentDialog by lazy { CommentDialogFragment() }
    var curPos: Int = 0

    override fun initView() {

        viewModel.dispatch(VideoIntent.LoadVideoList)
        initVideoView()
        initViewPager()

    }


    private fun initVideoView() {

        videoView.setLooping(true)
        //以下只能二选一
        videoView.setRenderViewFactory(TikTokRenderViewFactory.create())
//        videoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP)
        videoView.setVideoController(controller)
    }

    private fun initViewPager() {
        binding.page.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.page.adapter = adapter
        binding.page.offscreenPageLimit = 5
        binding.page.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private var mCurItem = -1

            //是否是反向滑动
            private var isReverseScroll = false

            //滑动中
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                if (position == mCurItem) return
                //如果是反向滑动
                isReverseScroll = position < mCurItem
            }

            //滑动到某一页
            override fun onPageSelected(position: Int) {
                if (position == curPos) return
                binding.page.post {
                    startPlay(position)
                }
                val videoSize = adapter.data.size
                if (position > videoSize - 5) {
                    viewModel.dispatch(VideoIntent.LoadVideoList)
                }
            }

            //滑动状态改变
            override fun onPageScrollStateChanged(state: Int) {
                //如果是拖拽状态
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    mCurItem = binding.page.currentItem
                }
                //如果是空闲状态
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    preloadManager.resumePreload(curPos, isReverseScroll)
                } else {
                    preloadManager.pausePreload(curPos, isReverseScroll)
                }
            }
        })
    }

    private fun startPlay(position: Int) {
        val count = viewPagerImpl.childCount
        for (i in 0 until count) {
            val itemView = viewPagerImpl.getChildAt(i)
            val holder = itemView.tag as VideoAdapterHolder
            if (holder.adapterPosition == position) {
                videoView.release()
                videoView.removeViewFormParent()
                val bean = adapter.data[position]
                val playUrl = AESUtils.decryptImg(bean.joke.videoUrl)
                videoView.setUrl(playUrl)
                controller.addControlComponent(holder.binding?.tiktokView, true)
                holder.binding?.container?.addView(videoView, 0)
                videoView.start()
                curPos = position
                break
            }
        }
    }

    override fun listener() {
        viewModel.state.observe(this, Observer {
            it.list?.run {
                val size = adapter.data.size
                adapter.data.addAll(this)
                adapter.notifyItemRangeChanged(size, adapter.data.size - size)
                Timber.e("size = $size ,   ${adapter.data.size}")
                if (size == 0) {
                    binding.page.post {
                        startPlay(0)
                    }
                }
            }
        })

        adapter.addChildClickViewIds(R.id.comment_img)
        adapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.comment_img -> {
                    val bean = adapter.data[position]
                    commentDialog.show(childFragmentManager, bean.info.commentNum.toString())
                }
            }
        }
    }





    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.release()
        preloadManager.removeAllPreloadTask()
    }

}