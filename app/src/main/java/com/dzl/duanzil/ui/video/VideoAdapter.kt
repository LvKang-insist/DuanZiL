package com.dzl.duanzil.ui.video

import android.view.View
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.databinding.FragVideoItemBinding
import com.dzl.duanzil.utils.AESUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 * @name VideoAdapter
 * @package com.dzl.duanzil.ui.video
 * @author 345 QQ:1831712732
 * @time 2022/08/25 15:02
 * @description
 */
class VideoAdapter :
    BaseQuickAdapter<VideoListBean.VideoListBeanItem, VideoAdapter.VideoAdapterHolder>(R.layout.frag_video_item) {


    override fun convert(holder: VideoAdapterHolder, item: VideoListBean.VideoListBeanItem) {
        holder.onBind(holder.adapterPosition, item)

//        DataBindingUtil.bind<FragVideoItemBinding>(holder.itemView)?.run {
//
//            player.setUpLazy(url, true, null, null, "")
//            player.titleTextView.visibility = View.GONE
//            player.backButton.visibility = View.GONE
//            player.playTag = TAG
//            player.playPosition = holder.adapterPosition
//            player.setIsTouchWiget(false)
//            player.isShowFullAnimation = true
//        }true
    }


    class VideoAdapterHolder(view: View) :
        BaseViewHolder(view) {

        val binding = DataBindingUtil.bind<FragVideoItemBinding>(view)!!
        val gsyVideoOptionBuilder = GSYVideoOptionBuilder()

        companion object {
            const val TAG = "VIDEO_ADAPTER"
        }


        fun onBind(position: Int, bean: VideoListBean.VideoListBeanItem) {

            val url = AESUtils.decryptImg(bean.joke.videoUrl)
            val thumbUrl = AESUtils.decryptImg(bean.joke.thumbUrl)

            //防止错位，离开释放
            //gsyVideoPlayer.initUIState();

            //防止错位，离开释放
            //gsyVideoPlayer.initUIState();
            gsyVideoOptionBuilder
                .setIsTouchWiget(false) //.setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle("")
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .build(binding.player)

            //增加title
            binding.player.titleTextView.visibility = View.GONE


            //设置返回键
            binding.player.backButton.visibility = View.GONE


            //设置全屏按键功能
            binding.player.fullscreenButton
                .setOnClickListener { resolveFullBtn(binding.player) }
        }


        /**
         * 全屏幕按键处理
         */
        private fun resolveFullBtn(standardGSYVideoPlayer: StandardGSYVideoPlayer) {
            standardGSYVideoPlayer.startWindowFullscreen(binding.player.context, true, true)
        }


        fun getPlayer(): StandardGSYVideoPlayer {
            return binding.player
        }
    }

}