package com.dzl.duanzil.bean

/**
 * @name AttentionRecommend
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/03 12:28
 * @description
 */
class AttentionRecommendBean : ArrayList<AttentionRecommendBean.AttentionRecommendBeanItem>() {
    data class AttentionRecommendBeanItem(
        val avatar: String,
        val fansNum: String,
        val isAttention: Boolean,
        val jokesNum: String,
        val nickname: String,
        val userId: Int
    )
}