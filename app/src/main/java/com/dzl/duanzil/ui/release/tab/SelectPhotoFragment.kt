package com.dzl.duanzil.ui.release.tab

import android.content.res.Configuration
import android.widget.Toast
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.core.other.GlideEngine
import com.dzl.duanzil.core.other.ImageCropEngine
import com.dzl.duanzil.core.other.PictureSelectorStyle
import com.dzl.duanzil.databinding.FragSelectPhotoBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.showStatusBar
import com.luck.picture.lib.basic.IBridgePictureBehavior
import com.luck.picture.lib.basic.PictureCommonFragment
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

/**
 * @name SelectPhotoFragment
 * @package com.dzl.duanzil.ui.release.tab
 * @author 345 QQ:1831712732
 * @time 2022/08/24 16:29
 * @description
 */
class SelectPhotoFragment : BaseBindingFragment<FragSelectPhotoBinding>(), IBridgePictureBehavior {



    override fun getLayoutId(): Int = R.layout.frag_select_photo

    override fun initView() {
        bindView()
    }

    private fun bindView() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofAll())
            .setSelectorUIStyle(PictureSelectorStyle(requireContext()))
            .setImageEngine(GlideEngine.createGlideEngine())
            .isDisplayCamera(false)
            .isPreviewAudio(true)
            .isPreviewImage(true)
            .isPreviewVideo(true)
            .setFilterVideoMaxSecond(15)
            .setCropEngine(ImageCropEngine())
            .isWithSelectVideoImage(true)//支持同时选择图片和视频
            .isPreviewFullScreenMode(true)
            .isEmptyResultReturn(true)
            .buildLaunch(
                binding.fragmentContainer.id,
                object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>?) {
                        result?.forEach {
                            if (PictureMimeType.isHasVideo(it.mimeType)) {
                                Toast.makeText(
                                    context,
                                    "视频 大小 ${(it.size / 1024.0 / 1024.0)}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "图片 大小 ${(it.size / 1024.0 / 1024.0)}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        requireActivity().finish()
                    }

                    override fun onCancel() {
                        requireActivity().finish()
                    }
                })
    }

    override fun onSelectFinish(result: PictureCommonFragment.SelectorResult?) {

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        bindView()
    }
}