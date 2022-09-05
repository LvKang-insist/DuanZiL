package com.dzl.duanzil.ui.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dzl.duanzil.bean.VideoListBean

/**
 * @name VideoAdapter
 * @package com.dzl.duanzil.ui.video
 * @author 345 QQ:1831712732
 * @time 2022/08/25 15:02
 * @description
 */


class VideoAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        const val TAG = "video_adapter"
    }

    var list: ArrayList<VideoListBean.VideoListBeanItem> = arrayListOf()

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle().apply {
            putSerializable("bean", list[position])
            putInt("position", position)
        }
        val fragment = VideoTabFragment()
        fragment.arguments = bundle
        return fragment
    }

}

/*
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


    class VideoAdapterHolder(val view: View) :
        BaseViewHolder(view) {

        val binding = DataBindingUtil.bind<FragVideoItemBinding>(view)!!
        val gsyVideoOptionBuilder = GSYVideoOptionBuilder()

        companion object {
            const val TAG = "VIDEO_ADAPTER"
        }


        fun onBind(position: Int, bean: VideoListBean.VideoListBeanItem) {

            val url = AESUtils.decryptImg(bean.joke.videoUrl)
            val thumbUrl = AESUtils.decryptImg(bean.joke.thumbUrl)

            val thumbImage = AppCompatImageView(view.context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            GlideAppUtils.loadImage(thumbImage.context, thumbUrl, thumbImage)


            gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(url)
                .setVideoTitle("")
                .setCacheWithPlay(false)
                .setRotateViewAuto(false)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setThumbImageView(thumbImage)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .build(binding.player)

            //增加title
            binding.player.titleTextView.visibility = View.GONE

            //设置返回键
            binding.player.backButton.visibility = View.GONE

//            //设置全屏按键功能
//            binding.player.fullscreenButton
//                .setOnClickListener { resolveFullBtn(binding.player) }
        }


        */
/**
 * 全屏幕按键处理
 *//*

        private fun resolveFullBtn(standardGSYVideoPlayer: StandardGSYVideoPlayer) {
            standardGSYVideoPlayer.startWindowFullscreen(binding.player.context, true, true)
        }


        fun getPlayer(): StandardGSYVideoPlayer {
            return binding.player
        }
    }

}*/
