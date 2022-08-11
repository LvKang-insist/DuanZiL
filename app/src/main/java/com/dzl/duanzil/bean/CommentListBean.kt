package com.dzl.duanzil.bean

import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.entity.node.NodeFooterImp

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
        val itemCommentList: CommentListItemBean?,
        val itemCommentNum: Int, // 0
        val jokeId: Int, // 0
        val jokeOwnerUserId: Int, // 0
        val likeNum: Int, // 0
        val timeStr: String
    ) : BaseNode(), NodeFooterImp {
        data class CommentUser(
            val nickname: String,
            val userAvatar: String,
            val userId: Int // 0
        )

        override val childNode: MutableList<BaseNode>?
            get() = if (itemCommentList == null) null
            else if (itemCommentList.size > 2) itemCommentList.subList(0, 2).toMutableList()
            else itemCommentList.toMutableList()

        override val footerNode: BaseNode?
            get() =
                if (itemCommentList == null || itemCommentList.size < 2)
                    null
                else CommentFooterBean("显示更多...")
    }
}