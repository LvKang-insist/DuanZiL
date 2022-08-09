package com.dzl.duanzil.bean

/**
 * @name JokeBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/09 21:17
 * @description
 */
data class JokeBean(
    val info: Info,
    val joke: Joke,
    val user: User
) {
    data class Info(
        val commentNum: Int, // 0
        val disLikeNum: Int, // 0
        val isAttention: Boolean, // true
        val isLike: Boolean, // true
        val isUnlike: Boolean, // true
        val likeNum: Int, // 0
        val shareNum: Int // 0
    )

    data class Joke(
        val addTime: String,
        val audit_msg: String,
        val content: String,
        val hot: Boolean, // true
        val imageSize: String,
        val imageUrl: String,
        val jokesId: Int, // 0
        val latitudeLongitude: String,
        val showAddress: String,
        val thumbUrl: String,
        val type: Int, // 0
        val userId: Int, // 0
        val videoSize: String,
        val videoTime: Int, // 0
        val videoUrl: String
    )

    data class User(
        val avatar: String,
        val nickName: String,
        val signature: String,
        val userId: Int // 0
    )
}