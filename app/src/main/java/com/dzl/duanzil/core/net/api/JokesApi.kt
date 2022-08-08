package com.dzl.duanzil.core.net.api

import com.dzl.duanzil.bean.AttentionListBean
import com.dzl.duanzil.core.net.ResponseData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @name JokesApi
 * @package com.dzl.duanzil.core.net.api
 * @author 345 QQ:1831712732
 * @time 2022/08/08 22:05
 * @description
 */
interface JokesApi {
    /** 给段子点赞-取消点赞 */
    @POST("home/pic")
    suspend fun jokesLike(@Body id: Int,@Body status:Boolean): ResponseData<AttentionListBean>

}