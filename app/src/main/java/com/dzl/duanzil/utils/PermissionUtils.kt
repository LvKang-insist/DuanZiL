package com.dzl.duanzil.utils

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hjq.toast.ToastUtils
import com.permissionx.guolindev.PermissionX

/**
 * @name PermissionUtils
 * @package com.tidycar.carzki.util
 * @author 345 QQ:1831712732
 * @time 2021/01/18 17:54
 * @description
 */


fun AppCompatActivity.applyPermission(block: () -> Unit) {
    PermissionX.init(this)
        .permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        .request { allGranted, _, _ ->
            if (allGranted) {
                block.invoke()
            } else {
                ToastUtils.show("您拒绝了权限，请在应用设置中手动打开")
            }
        }
}


fun AppCompatActivity.applyPermission(vararg permissions: String, block: () -> Unit) {
    PermissionX.init(this)
        .permissions(* permissions)
        .request { allGranted, _, _ ->
            if (allGranted) {
                block.invoke()
            } else {
                ToastUtils.show("您拒绝了权限，请在应用设置中手动打开")
            }
        }
}


fun Fragment.applyPermission(vararg permissions: String, block: () -> Unit) {
    PermissionX.init(this)
        .permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        .request { allGranted, _, _ ->
            if (allGranted) {
                block.invoke()
            } else {
                ToastUtils. show("您拒绝了权限，请在应用设置中手动打开")
            }
        }
}