package com.dzl.duanzil.app

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.dzl.duanzil.BuildConfig
import com.dzl.duanzil.R
import com.dzl.duanzil.core.net.interceptor.HeaderInterceptor
import com.dzl.duanzil.core.other.DebugLoggerTree
import com.hjq.toast.ToastUtils
import com.lvhttp.net.LvHttp
import com.lvhttp.net.error.CodeException
import com.lvhttp.net.error.ErrorKey
import com.lvhttp.net.error.ErrorValue
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.mmkv.MMKV
import timber.log.Timber

/**
 * @name BaseApp
 * @package com.dzl.duanzil.app
 * @author 345 QQ:1831712732
 * @time 2022/08/02 18:39
 * @description
 */
class AppApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initSdk(this)
    }

    companion object {
        lateinit var application: Application
        fun initSdk(application: Application) {
            this.application = application


            // 设置全局的 Header 构建器

            // 设置全局的 Header 构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { cx: Context?, layout: RefreshLayout? ->
                MaterialHeader(
                    application
                ).setColorSchemeColors(
                    ContextCompat.getColor(application, R.color.skin_primary)
                )
            }
            // 设置全局的 Footer 构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { cx: Context?, layout: RefreshLayout? ->
                ClassicsFooter(application).apply {
                    this.setPrimaryColorId(R.color.white)
                }
            }

            // 初始化日志打印
            if (BuildConfig.DEBUG) {
                Timber.plant(DebugLoggerTree())
            }

            //初始化 MMkv
            MMKV.initialize(application)

            //初始化网络框架
            LvHttp.Builder()
                .setApplication(application)
                .setBaseUrl(BuildConfig.API_BASE)
                //是否开启缓存
                .isCache(false)
                //是否打印 log
                .isLog(true)
                .addInterceptor(HeaderInterceptor())
                .setCode(200)
                //对 Code 异常的处理，可自定义,参考 ResponseData 类
                .setErrorDispose(ErrorKey.ErrorCode, ErrorValue {
                    (it as? CodeException)?.run {
                        ToastUtils.show("Code 错误  ${it.message}")
                    }
                })
                //全局异常处理，参考 Launch.kt ，可自定义异常处理，参考 ErrorKey 即可
                .setErrorDispose(ErrorKey.AllEexeption, ErrorValue {
                    it.printStackTrace()
                    ToastUtils.show(it.message)
                })
                .build()

            ToastUtils.init(application)

            // 注册网络状态变化监听
//            val connectivityManager = ContextCompat.getSystemService(
//                application,
//                ConnectivityManager::class.java
//            )
//            if (connectivityManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
//                    override fun onLost(network: Network) {
//                        val topActivity: Activity = ActivityManager.getInstance().getTopActivity()
//                        if (topActivity !is LifecycleOwner) {
//                            return
//                        }
//                        val lifecycleOwner = topActivity as LifecycleOwner
//                        if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) {
//                            return
//                        }
//                        ToastUtils.show(R.string.common_network_error)
//                    }
//                })
//            }

        }
    }
}