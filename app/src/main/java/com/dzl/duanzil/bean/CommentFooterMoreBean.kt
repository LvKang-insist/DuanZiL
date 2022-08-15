package com.dzl.duanzil.bean

import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @name CommentFooterMoreBean
 * @package com.dzl.duanzil.bean
 * @author 345 QQ:1831712732
 * @time 2022/08/12 15:11
 * @description
 */
class CommentFooterMoreBean(val commentId: Int) : BaseNode() {
    override val childNode: MutableList<BaseNode>
        get() = mutableListOf()
}