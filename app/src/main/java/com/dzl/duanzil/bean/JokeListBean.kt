package com.dzl.duanzil.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * @name AttentionListBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/04 10:36
 * @description
 */
class JokeListBean : ArrayList<JokeListBean.JokeListBeanItem>() {
    data class JokeListBeanItem(
        val info: Info,
        val joke: Joke,
        val user: User
    ) : MultiItemEntity, Serializable {
        data class Info(
            val commentNum: Int,
            val disLikeNum: Int,
            val isAttention: Boolean,
            val isLike: Boolean,
            val isUnlike: Boolean,
            val likeNum: Int,
            val shareNum: Int
        ) : Serializable

        data class Joke(
            val addTime: String,
            val audit_msg: String,
            val content: String,
            val hot: Boolean,
            val imageSize: String,
            val imageUrl: String,
            val jokesId: Int,
            val latitudeLongitude: String,
            val showAddress: String,
            val thumbUrl: String,
            val type: Int,
            val userId: Int,
            val videoSize: String,
            val videoTime: Int,
            val videoUrl: String
        ) : Serializable

        data class User(
            val avatar: String,
            val nickName: String,
            val signature: String,
            val userId: Int
        ) : Serializable

        //1,图片，2，文本，>=3，视频
        override val itemType: Int
            get() {
                if (joke.type <= 2) return joke.type
                return 3
            }
    }
}