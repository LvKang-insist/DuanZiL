package com.dzl.duanzil.ui.video

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.databinding.FragVideoItemBinding
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.GlideUtil
import com.dzl.duanzil.utils.cache.PreloadManager
import timber.log.Timber

/**
 * @name VideoAdapter
 * @package com.dzl.duanzil.ui.video
 * @author 345 QQ:1831712732
 * @time 2022/08/25 15:02
 * @description
 */


class VideoAdapter :
    BaseQuickAdapter<VideoListBean.VideoListBeanItem, VideoAdapterHolder>(R.layout.frag_video_item) {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: VideoAdapterHolder, item: VideoListBean.VideoListBeanItem) {
        val playUrl = AESUtils.decryptImg(item.joke.videoUrl)
        Timber.e("playUrl = $playUrl")
        PreloadManager.getInstance(holder.view.context)
            .addPreloadTask(playUrl, holder.adapterPosition)
        holder.thumb?.run {
            val thumbUrl = AESUtils.decryptImg(item.joke.thumbUrl)
            Glide.with(context)
                .load(thumbUrl)
                .placeholder(R.color.black)
                .into(this)
        }
        holder.binding?.run {
            name.isSelected = true
            name.text = "@${item.user.nickName} 发布的视频"
            content.text = item.joke.content
            GlideUtil.loadImageCropCircleWithBorder(
                context, item.user.avatar, avatar, 5,
                ResourcesCompat.getColor(context.resources, R.color.white, null)
            )
            commentText.text = item.info.commentNum.toString()
            likeImg.setImageResource(if (item.info.isLike) R.drawable.video_like_on_icon else R.drawable.video_like_off_icon)
            likeText.text = item.info.likeNum.toString()
            shareText.text = item.info.shareNum.toString()
            collectText.text = item.info.disLikeNum.toString()

        }
        holder.view.tag = holder
    }

    //该方法在view被回收时调用
    override fun onViewDetachedFromWindow(holder: VideoAdapterHolder) {
        super.onViewDetachedFromWindow(holder)
        val item = data[holder.adapterPosition]
        //移除预加载任务
        val playUrl = AESUtils.decryptImg(item.joke.videoUrl)
        PreloadManager.getInstance(holder.view.context).removePreloadTask(playUrl)
    }

    override fun getItemCount(): Int = data.size


}

class VideoAdapterHolder(val view: View) : BaseViewHolder(view) {
    val binding by lazy { DataBindingUtil.bind<FragVideoItemBinding>(view) }
    val thumb by lazy { binding?.tiktokView?.findViewById<AppCompatImageView>(R.id.iv_thumb) }
}
