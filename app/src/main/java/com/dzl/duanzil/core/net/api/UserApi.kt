package com.dzl.duanzil.core.net.api

import com.dzl.duanzil.bean.LoginBean
import com.dzl.duanzil.core.net.ResponseData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @name UserApi
 * @package com.dzl.duanzil.core.net.api
 * @author 345 QQ:1831712732
 * @time 2022/08/11 18:09
 * @description
 */
interface UserApi {


    /** 获取评论列表 */
    @POST("user/login/code")
    @FormUrlEncoded
    suspend fun loginCode(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): ResponseData<LoginBean>

    /** 发送验证码 */
    @POST("user/login/get_code")
    @FormUrlEncoded
    suspend fun sendCode(
        @Field("phone") phone: String,
    ): ResponseData<Any>

}