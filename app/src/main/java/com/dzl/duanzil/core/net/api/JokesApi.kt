package com.dzl.duanzil.core.net.api

import com.dzl.duanzil.bean.CommentListBean
import com.dzl.duanzil.bean.JokeBean
import com.dzl.duanzil.bean.JokeListBean
import com.dzl.duanzil.core.net.ResponseData
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    suspend fun jokesLike(@Body id: Int, @Body status: Boolean): ResponseData<JokeListBean>


    /** 获取指定 id 的段子 */
    @POST("jokes/target")
    suspend fun jokesTarget(@Body jokeId: Int): ResponseData<JokeBean>


    /** 获取评论列表 */
    @POST("jokes/comment/list")
    @FormUrlEncoded
    suspend fun jokesCommentList(
        @Field("jokeId") jokeId: Int,
        @Field("page") page: Int
    ): ResponseData<CommentListBean>

    /** 获取评论列表 */
    @POST("jokes/comment/list/item")
    suspend fun jokesCommentListItem(
        @Body jokeId: Int,
        @Body page: Int
    ): ResponseData<CommentListBean>


}