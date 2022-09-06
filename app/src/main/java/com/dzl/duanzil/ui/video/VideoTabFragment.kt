package com.dzl.duanzil.ui.video

import android.os.Bundle
import android.view.View
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragVideoTabItemBinding
import com.dzl.duanzil.utils.AESUtils
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import timber.log.Timber

/**
 * @name VideoTabFragment
 * @package com.dzl.duanzil.ui.video
 * @author 345 QQ:1831712732
 * @time 2022/09/02 17:25
 * @description
 */
class VideoTabFragment : BaseBindingFragment<FragVideoTabItemBinding>() {


    val bean by lazy { arguments?.getSerializable("bean") as VideoListBean.VideoListBeanItem }
    val position by lazy { arguments?.getInt("position") ?: -1 }
    val url by lazy { AESUtils.decryptImg(bean.joke.videoUrl) }
    val thumbUrl by lazy { AESUtils.decryptImg(bean.joke.thumbUrl) }

    val gsyVideoOptionBuilder = GSYVideoOptionBuilder()

    var seekOnStart = 0L

    override fun getLayoutId(): Int = R.layout.frag_video_tab_item

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.player.loadCoverImage(thumbUrl, R.drawable.like_on_ic)
        gsyVideoOptionBuilder
            .setIsTouchWiget(false)
            .setUrl(url)
            .setVideoTitle("")
            .setCacheWithPlay(false)
            .setRotateViewAuto(false)
            .setLockLand(true)
            .setPlayTag(VideoAdapter.TAG)
            .setShowFullAnimation(true)
//            .setThumbImageView(thumbImage)
            .setNeedLockFull(true)
            .setPlayPosition(position)
            .build(binding.player)

        //增加title
        binding.player.titleTextView.visibility = View.GONE

        //设置返回键
        binding.player.backButton.visibility = View.GONE

//        binding.player.setVideoAllCallBack(object : VideoAllCallBack)

//            //设置全屏按键功能
//            binding.player.fullscreenButton
//                .setOnClickListener { resolveFullBtn(binding.player) }
    }

    override fun initView() {
        Timber.e("------------- >1  ${thumbUrl}")
//        binding.player.setShowPauseCover(true)
        binding.player.startPlayLogic()
    }


    override fun onResume() {
        Timber.e("------------- >2")
        if(seekOnStart >0){
            binding.player.seekOnStart = seekOnStart
        }
        binding.player.startPlayLogic()
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        seekOnStart = binding.player.currentPositionWhenPlaying
        binding.player.onVideoPause()
    }


}