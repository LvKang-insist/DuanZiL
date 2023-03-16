package com.dzl.duanzil.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.dzl.duanzil.app.GlideApp
import jp.wasabeef.glide.transformations.*
import jp.wasabeef.glide.transformations.RoundedCornersTransformation.CornerType

/**
 * @name GlideUtil
 * @package com.dzl.duanzil.utils
 * @author 345 QQ:1831712732
 * @time 2023/03/06 15:29
 * @description Glide v4 工具类
 */

object GlideUtil : AppGlideModule() {


    //加载图片
    fun loadImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    //加载渐变动画
    fun loadAnimImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    //加载圆角图片
    fun loadRoundImage(context: Context, url: String?, imageView: ImageView, radius: Int) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .transform(CenterCrop(), RoundedCorners(dp2px(context, radius.toFloat())))
            .into(imageView)
    }

    //加载圆角图片
    fun loadAnimRoundImage(context: Context, url: String?, imageView: ImageView, radius: Int) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CenterCrop(), RoundedCorners(dp2px(context, radius.toFloat())))
            .into(imageView)
    }

    //glide加载图片并设置占位图和错误图
    fun loadImage(
        context: Context,
        url: String?,
        imageView: ImageView,
        @DrawableRes placeholder: Int,
        @DrawableRes error: Int
    ) {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.placeholderOf(placeholder).error(error)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(imageView)
    }

    //glide加载图片并设置占位图和错误图
    fun loadImage(
        context: Context,
        url: String?,
        imageView: ImageView,
        @DrawableRes placeholder: Int
    ) {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.placeholderOf(placeholder).error(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(imageView)
    }

    //glide加载图片并设置占位图和错误图
    fun loadImage(context: Context, url: String?, imageView: ImageView, placeholder: Drawable) {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.placeholderOf(placeholder).error(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(imageView)
    }

    //glide加载图片并设置占位图和错误图
    fun loadImage(
        context: Context,
        url: String?,
        imageView: ImageView,
        placeholder: Drawable,
        error: Drawable
    ) {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.placeholderOf(placeholder).error(error)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(imageView)
    }


    //加载圆形图片
    fun loadCircleImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    //加载高斯模糊图片
    fun loadBlurImage(context: Context, url: String?, imageView: ImageView, radius: Int) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(radius)))
            .into(imageView)
    }

    //加载灰度图片
    fun loadGrayImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(GrayscaleTransformation()))
            .into(imageView)
    }

    //加载圆角和灰度图片
    fun loadRoundGrayImage(context: Context, url: String?, imageView: ImageView, radius: Int) {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        GrayscaleTransformation(),
                        RoundedCornersTransformation(radius, 0, CornerType.ALL)
                    )
                )
            )
            .into(imageView)
    }

    //获取图片加载进度
    fun loadProgressImage(
        context: Context,
        url: String?,
        imageView: ImageView,
        progressListener: GlideProgressListener
    ) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
            .listener(GlideProgressInterceptor(progressListener))
            .into(imageView)
    }


    //清除图片磁盘缓存
    fun clearImageDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }

    //加载长图
    fun loadLongImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
            .into(object : CustomViewTarget<ImageView, Drawable>(imageView) {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                    imageView.setImageDrawable(errorDrawable)
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    imageView.setImageDrawable(placeholder)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.setImageDrawable(resource)
                }
            })
    }

    //加载gif
    fun loadGifImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .asGif()
            .load(url)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
            .into(imageView)
    }

    /**
     * 加载图片--圆形带边框（可自定义边框宽度和颜色）
     */
    fun loadImageCropCircleWithBorder(
        context: Context,
        url: String,
        imageView: ImageView,
        borderSize: Int,
        borderColor: Int
    ) {
        val options =
            RequestOptions()
                .centerCrop()
                .transform(
                    CropCircleWithBorderTransformation(
                        borderSize,
                        borderColor
                    )
                )
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    //dp转px
    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}

//Glide图片加载进度监听
interface GlideProgressListener {
    fun onProgress(progress: Int, isComplete: Boolean, isFailed: Boolean)
}

//Glide加载图片进度监听
class GlideProgressInterceptor(val progressListener: GlideProgressListener) :
    RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean
    ): Boolean {
        progressListener.onProgress(100, true, false)
        return false
    }

    override fun onResourceReady(
        resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource,
        isFirstResource: Boolean
    ): Boolean {
        progressListener.onProgress(100, true, false)
        return false
    }
}

