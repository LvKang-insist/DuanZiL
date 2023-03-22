package com.dzl.duanzil.app

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.danikula.videocache.Logger
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
import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager

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

            initPlayVideo()

            // 设置全局的 Header 构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { _: Context?, _: RefreshLayout? ->
                MaterialHeader(
                    application
                ).setColorSchemeColors(
                    ContextCompat.getColor(application, R.color.skin_primary)
                )
            }
            // 设置全局的 Footer 构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { _: Context?, _: RefreshLayout? ->
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

        private fun initPlayVideo() {
            //播放器配置，注意：此为全局配置，按需开启
            VideoViewManager.setConfig(
                VideoViewConfig.newBuilder()
                    .setLogEnabled(xyz.doikki.videoplayer.BuildConfig.DEBUG) //调试的时候请打开日志，方便排错
                    /** 软解，支持格式较多，可通过自编译so扩展格式，结合 [xyz.doikki.dkplayer.widget.videoview.IjkVideoView] 使用更佳  */ //
//                    .setPlayerFactory(IjkPlayerFactory.create())
//                    .setPlayerFactory(AndroidMediaPlayerFactory.create()) //不推荐使用，兼容性较差
                    /** 硬解，支持格式看手机，请使用CpuInfoActivity检查手机支持的格式，结合 [xyz.doikki.dkplayer.widget.videoview.ExoVideoView] 使用更佳  */
                    .setPlayerFactory(ExoMediaPlayerFactory.create()) // 设置自己的渲染view，内部默认TextureView实现
//                    .setRenderViewFactory(SurfaceRenderViewFactory.create())
                    // 根据手机重力感应自动切换横竖屏，默认false
//                    .setEnableOrientation(true)
                    // 监听系统中其他播放器是否获取音频焦点，实现不与其他播放器同时播放的效果，默认true
//                    .setEnableAudioFocus(false)
                    // 视频画面缩放模式，默认按视频宽高比居中显示在VideoView中
//                    .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                    // 适配刘海屏，默认true
//                    .setAdaptCutout(false)
                    // 移动网络下提示用户会产生流量费用，默认不提示，
                    // 如果要提示则设置成false并在控制器中监听STATE_START_ABORT状态，实现相关界面，具体可以参考PrepareView的实现
//                    .setPlayOnMobileNetwork(false)
                    // 进度管理器，继承ProgressManager，实现自己的管理逻辑
//                    .setProgressManager(new ProgressManagerImpl ())
                    .build()
            )

//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
            // VideoCache 日志
//            Logger.setDebug(xyz.doikki.videoplayer.BuildConfig.DEBUG)
        }
    }
}