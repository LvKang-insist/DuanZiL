package com.dzl.duanzil.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragVideoTabItemBinding
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.GlideAppUtils
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder

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
            .setLooping(true)
            .setPlayPosition(position)
            .build(binding.player)

        //增加title
        binding.player.titleTextView.visibility = View.GONE

        //设置返回键
        binding.player.backButton.visibility = View.GONE

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.player.isShowPauseCover = true
        binding.name.isSelected = true
        binding.name.text = "@${bean.user.nickName} 发布的视频"
        binding.content.text = bean.joke.content

        GlideAppUtils.loadImageCropCircleWithBorder(
            requireContext(),
            bean.user.avatar,
            binding.avatar,
            5,
            ResourcesCompat.getColor(resources, R.color.white, null)
        )
    }


    override fun onResume() {
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