package com.dzl.duanzil.ui.release.tab

import android.util.Log
import com.dzl.duanzil.core.base.BaseCameraFragment
import com.dzl.duanzil.utils.PathUtils
import com.luck.lib.camerax.CustomCameraConfig

/**
 * @name CameraVideoFragment
 * @package com.dzl.duanzil.ui.release.tab
 * @author 345 QQ:1831712732
 * @time 2022/08/24 16:29
 * @description
 */
class CameraVideoFragment : BaseCameraFragment() {

    override fun setCameraMode(): Int = CustomCameraConfig.BUTTON_STATE_ONLY_RECORDER

    override fun outputPathDir(): String = PathUtils.getCacheVideoPath(requireContext())

    override fun handleCameraSuccess(url: String) {
        Log.e("---345--->", url)
    }

    override fun handleCameraCancel() {
        super.handleCameraCancel()
    }

}