package com.dzl.duanzil.ui.release.tab


import com.dzl.duanzil.core.base.BaseCameraFragment
import com.dzl.duanzil.utils.PathUtils
import com.luck.lib.camerax.CustomCameraConfig
import timber.log.Timber

/**
 * @name CameraImageFragment
 * @package com.dzl.duanzil.ui.release.tab
 * @author 345 QQ:1831712732
 * @time 2022/08/24 16:26
 * @description
 */
class CameraImageFragment : BaseCameraFragment() {
    override fun setCameraMode(): Int = CustomCameraConfig.BUTTON_STATE_ONLY_CAPTURE

    override fun outputPathDir(): String = PathUtils.getCacheImagePath(requireContext())

    override fun handleCameraSuccess(url: String) {
        Timber.e("---345--->  $url")
        requireActivity().finish()
    }

    override fun handleCameraCancel() {
        super.handleCameraCancel()
    }
}