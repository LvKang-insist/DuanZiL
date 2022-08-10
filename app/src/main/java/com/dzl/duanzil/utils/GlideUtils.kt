package com.dzl.duanzil.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dzl.duanzil.app.GlideApp
import jp.wasabeef.glide.transformations.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @name GlideUtils
 * @package com.dzl.duanzil.utils
 * @author 345 QQ:1831712732
 * @time 2022/08/04 17:31
 * @description
 */
object GlideAppUtils {

    /**
     * 加载图片
     */
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context).load(url).into(imageView)
    }

    /**
     * 加载图片--中间裁剪
     */
    fun loadImageCenterCrop(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context).load(url).centerCrop().into(imageView)
    }

    fun loadImageMxHeight(context: Context, url: String, imageView: ImageView, radius: Int = 0) {
        val options =
//            RequestOptions().transform(
//                MultiTransformation(
////                    CenterCrop(),
////                    RoundedCorners(radius)
//                )
//            )
        GlideApp.with(context).load(url).override(imageView.width, 100).into(imageView)
    }

    /**
     * 加载图片--圆形
     */
    fun loadImageCircleCrop(
        context: Context, url: String, imageView: ImageView
    ) {
        GlideApp.with(context).load(url).circleCrop().into(imageView)
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

    /**
     * 加载图片--圆角--四边全圆角 （自定义弧度半径）
     * 注：centerCrop() 与 RoundedCornersTransformatin存在冲突，如果同时使用的话这里采用 MultiTransformation
     */
    fun loadImageRound(
        context: Context,
        url: String,
        imageView: ImageView,
        radius: Int
    ) {

        val options =
            RequestOptions().transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(radius)
                )
            )
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--圆角--指定圆角(上下左右可任意指定) （自定义弧度半径和边距）
     */
    fun loadImageRound(
        context: Context,
        url: String,
        imageView: ImageView,
        radius: Int,
        margin: Int,
        cornerType: RoundedCornersTransformation.CornerType
    ) {
        val options =
            RequestOptions()
                .transform(RoundedCornersTransformation(radius, margin, cornerType))
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--灰度-黑白
     */
    fun loadImageGrayscale(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        val options =
            RequestOptions()
                .centerCrop()
                .transform(GrayscaleTransformation())
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--模糊效果（毛玻璃效果）
     */
    fun loadImageBlur(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        val options =
            RequestOptions()
                .centerCrop()
                .transform(BlurTransformation())
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    /**
     * 加载图片--颜色滤镜
     */
    fun loadImageColorFilter(
        context: Context,
        url: String,
        imageView: ImageView,
        color: Int
    ) {
        val options =
            RequestOptions()
                .centerCrop()
                .transform(ColorFilterTransformation(color))
        GlideApp.with(context).load(url).apply(options).into(imageView)
    }

    fun loadBitmap(activity: AppCompatActivity, url: String, block: (Bitmap) -> Unit) {
        Timber.e("--------- $url")
        activity.lifecycleScope.launch(Dispatchers.IO) {
            val bitmap = GlideApp.with(activity)
                .asBitmap()
                .load(url)
                .submit()
                .get()
            launch(Dispatchers.Main) { block.invoke(bitmap) }
        }
    }

}