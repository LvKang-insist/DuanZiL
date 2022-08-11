package com.dzl.duanzil.bean

import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @name CommentListitemBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/11 11:05
 * @description
 */
class CommentListItemBean : ArrayList<CommentListItemBean.CommentListItemBeanItem>() {
    data class CommentListItemBeanItem(
        val commentItemId: Int,
        val commentParentId: Int,
        val commentUser: CommentUser,
        val commentedNickname: String,
        val commentedUserId: Int,
        val content: String,
        val isReplyChild: Boolean,
        val jokeId: Int,
        val timeStr: String
    ) : BaseNode() {
        data class CommentUser(
            val nickname: String,
            val userAvatar: String,
            val userId: Int
        )

        override val childNode: MutableList<BaseNode>?
            get() = null
    }
}