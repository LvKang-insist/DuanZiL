package com.dzl.duanzil.bean

/**
 * @name CommentListBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/09 21:32
 * @description
 */
data class CommentListBean(
    val comments: List<Comment>,
    val count: Int // 0
) {
    data class Comment(
        val commentId: Int, // 0
        val commentUser: CommentUser,
        val content: String,
        val isLike: Boolean, // true
        val itemCommentList: List<ItemComment>,
        val itemCommentNum: Int, // 0
        val jokeId: Int, // 0
        val jokeOwnerUserId: Int, // 0
        val likeNum: Int, // 0
        val timeStr: String
    ) {
        data class CommentUser(
            val nickname: String,
            val userAvatar: String,
            val userId: Int // 0
        )

        data class ItemComment(
            val commentItemId: Int, // 0
            val commentParentId: Int, // 0
            val commentUser: CommentUser,
            val commentedNickname: String,
            val commentedUserId: Int, // 0
            val content: String,
            val isReplyChild: Boolean, // true
            val jokeId: Int, // 0
            val timeStr: String
        ) {
            data class CommentUser(
                val nickname: String,
                val userAvatar: String,
                val userId: Int // 0
            )
        }
    }
}