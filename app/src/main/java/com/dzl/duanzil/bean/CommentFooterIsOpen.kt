package com.dzl.duanzil.bean

import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @name CommentFooterBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/11 11:32
 * @description
 */
class CommentFooterIsOpen(val commentId: Int,val  text: String) : BaseNode() {
    override val childNode: MutableList<BaseNode>
        get() = mutableListOf()
}