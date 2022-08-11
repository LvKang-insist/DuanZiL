package com.dzl.duanzil.bean

/**
 * @name LoginBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/11 18:11
 * @description
 */
data class LoginBean(
    val token: String,
    val type: String,
    val userInfo: UserInfo
) {
    data class UserInfo(
        val avatar: String,
        val birthday: String,
        val inviteCode: String,
        val invitedCode: String,
        val nickname: String,
        val sex: String,
        val signature: String,
        val userId: Int,
        val userPhone: String
    )
}