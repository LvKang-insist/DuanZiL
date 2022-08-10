package com.dzl.duanzil.core.net.api

import com.dzl.duanzil.bean.JokeListBean
import com.dzl.duanzil.bean.AttentionRecommendBean
import com.dzl.duanzil.core.net.ResponseData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @name HomeApi
 * @package com.dzl.duanzil.core.net.api
 * @author 345 QQ:1831712732
 * @time 2022/08/02 21:29
 * @description
 */
interface HomeApi {

    /** 主页推荐关注数据 */
    @POST("home/attention/recommend")
    suspend fun homeAttentionRecommend(): ResponseData<AttentionRecommendBean>

    /** 获取关注的用户发布的段子列表 */
    @POST("home/attention/list")
    suspend fun attentionList(@Body page: Int): ResponseData<JokeListBean>

    /** 获取主页的最新列表数据 */
    @POST("home/latest")
    suspend fun homeLatest(@Body page: Int): ResponseData<JokeListBean>

    /** 获取主页的推荐列表数据 */
    @POST("home/recommend")
    suspend fun homeRecommend(@Body page: Int): ResponseData<JokeListBean>


    /** 获取主页的推荐列表数据 */
    @POST("home/pic")
    suspend fun homePic(@Body page: Int): ResponseData<JokeListBean>

    /** 获取主页的纯文列表数据 */
    @POST("home/text")
    suspend fun homeText(@Body page: Int): ResponseData<JokeListBean>


}