package com.dzl.duanzil.ui.video

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.cache.PreloadManager
import com.dzl.duanzil.widgets.component.TikTokView

/**
 * @name VideoAdapter
 * @package com.dzl.duanzil.ui.video
 * @author 345 QQ:1831712732
 * @time 2022/08/25 15:02
 * @description
 */


class VideoAdapter :
    BaseQuickAdapter<VideoListBean.VideoListBeanItem, VideoAdapterHolder>(R.layout.frag_video_item) {


    override fun convert(holder: VideoAdapterHolder, item: VideoListBean.VideoListBeanItem) {
        val playUrl = AESUtils.decryptImg(item.joke.videoUrl)
        PreloadManager.getInstance(holder.view.context)
            .addPreloadTask(playUrl, holder.adapterPosition)

        Glide.with(context)
            .load(item.joke.thumbUrl)
            .placeholder(android.R.color.white)
            .into(holder.thumb)

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
    val tiktok: TikTokView by lazy { view.findViewById<TikTokView>(R.id.tiktok_View) }
    val container: FrameLayout by lazy { view.findViewById<FrameLayout>(R.id.container) }
    val thumb: AppCompatImageView by lazy { view.findViewById<AppCompatImageView>(R.id.thumb) }
}
