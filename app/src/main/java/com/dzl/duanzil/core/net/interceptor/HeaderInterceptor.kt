package com.dzl.duanzil.core.net.interceptor

import com.dzl.duanzil.extension.MMkvEnum
import com.dzl.duanzil.extension.getString
import com.dzl.duanzil.utils.AppUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by april on 2018/1/10.
 */
class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("uk", AppUtil.uniqueDeviceId ?: AppUtil.androidID)
            .addHeader("device", AppUtil.model)
            .addHeader("app", "${AppUtil.appVersionName};${AppUtil.sDKVersionName}")
            .addHeader("channel", "cretin_open_api")
            .addHeader("project_token", "AEC592A8D7CE4B1EBEE8D3DA2A89BCC0")
            .addHeader("token", MMkvEnum.TOKEN.getString())
            .build()

        return chain.proceed(request)
    }

}