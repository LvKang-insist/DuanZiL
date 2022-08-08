package com.dzl.duanzil.utils

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import com.dzl.duanzil.app.AppApplication
import java.util.*

/**
 * APP常用工具类，包括获取版本号等
 */
object AppUtil {

    /** 获取版本号名称 */
    val appVersionName: String
        get() {
            val packageInfo =
                AppApplication.application.packageManager.getPackageInfo(
                    AppApplication.application.packageName,
                    0
                )
            return packageInfo.versionName
        }


    /** 获取版本号 */
    val appVersion: Long
        get() {
            val packageInfo =
                AppApplication.application.packageManager.getPackageInfo(
                    AppApplication.application.packageName,
                    0
                )
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) packageInfo.longVersionCode
            else packageInfo.versionCode.toLong()
        }


    /** 设备系统版本名称 */
    val sDKVersionName: String
        get() = Build.VERSION.RELEASE


    /** 设备系统版本号 */
    val sDKVersionCode: Int
        get() = Build.VERSION.SDK_INT

    /** 返回制造商 */
    val manufacturer: String
        get() = Build.MANUFACTURER

    /** 设备型号 */
    val model: String
        get() {
            var model = Build.MODEL
            model = model?.trim { it <= ' ' }?.replace("\\s*".toRegex(), "") ?: ""
            return model
        }

    @Volatile
    private var udid: String? = null

    /**
     * 唯一的设备 id
     */
    val uniqueDeviceId: String?
        get() = getUniqueDeviceId("")

    fun getUniqueDeviceId(prefix: String): String? {
        return if (udid == null) {
            getUniqueDeviceIdReal(prefix)
        } else udid
    }

    private fun getUniqueDeviceIdReal(prefix: String): String? {
        try {
            val androidId = androidID
            if (!TextUtils.isEmpty(androidId)) {
                return saveUdid(prefix + 2, androidId)
            }
        } catch (ignore: Exception) { /**/
        }
        return saveUdid(prefix + 9, "")
    }


    /**
     * Return the android id of device.
     *
     * @return the android id of device
     */
    @get:SuppressLint("HardwareIds")
    val androidID: String
        get() {
            val id = Settings.Secure.getString(
                AppApplication.application.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            return if ("9774d56d682e549c" == id) "" else id ?: ""
        }

    private fun saveUdid(prefix: String, id: String): String? {
        udid = getUdid(prefix, id)
        return udid
    }

    private fun getUdid(prefix: String, id: String): String {
        return if (id == "") {
            prefix + UUID.randomUUID().toString().replace("-", "")
        } else prefix + UUID.nameUUIDFromBytes(id.toByteArray()).toString()
            .replace("-", "")
    }
}