package com.dzl.duanzil.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.CommentFooterBean
import com.dzl.duanzil.bean.CommentListBean
import com.dzl.duanzil.bean.CommentListItemBean
import com.dzl.duanzil.utils.GlideAppUtils

/**
 * @name JokeCommentAdapter
 * @package com.dzl.duanzil.ui.adapter
 * @author 345 QQ:1831712732
 * @time 2022/08/11 11:00
 * @description
 */
class JokeCommentAdapter : BaseNodeAdapter() {

    init {
        addFullSpanNodeProvider(CommentRootProvider())
        addNodeProvider(CommentNodeProvider())
        addFooterNodeProvider(CommentFooterNodeProvider())
    }

    override fun getItemType(data: List<BaseNode>, position: Int): Int = when (data[position]) {
        is CommentListBean.Comment -> 0
        is CommentListItemBean.CommentListItemBeanItem -> 1
        is CommentFooterBean -> 2
        else -> -1
    }

}


private class CommentRootProvider : BaseNodeProvider() {
    override val itemViewType: Int
        get() = 0
    override val layoutId: Int
        get() = R.layout.comment_root_item

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as? CommentListBean.Comment)?.let {
            helper.setText(R.id.name, it.commentUser.nickname)
            GlideAppUtils.loadImage(
                context, item.commentUser.userAvatar,
                helper.getView(R.id.avatar)
            )
            helper.setText(R.id.content, it.content)
            helper.setText(R.id.like_count, it.likeNum.toString())
            if (item.isLike) {
                helper.setBackgroundResource(R.id.like, R.drawable.like_on_ic)
            } else {
                helper.setBackgroundResource(R.id.like, R.drawable.like_off_ic)
            }
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {

    }

}

private class CommentNodeProvider : BaseNodeProvider() {
    override val itemViewType: Int
        get() = 1
    override val layoutId: Int
        get() = R.layout.comment_node_item

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as? CommentListItemBean.CommentListItemBeanItem)?.let {
            helper.setText(R.id.name, it.commentUser.nickname)
            GlideAppUtils.loadImage(
                context, item.commentUser.userAvatar,
                helper.getView(R.id.avatar)
            )
            helper.setText(R.id.content, it.content)
//            helper.setText(R.id.like_count, it.likeNum)
//            if (item.isLike) {
//                helper.setBackgroundResource(R.id.like, R.drawable.like_on_ic)
//            } else {
//                helper.setBackgroundResource(R.id.like, R.drawable.like_off_ic)
//            }
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {


    }

}


private class CommentFooterNodeProvider : BaseNodeProvider() {
    override val itemViewType: Int
        get() = 2
    override val layoutId: Int
        get() = R.layout.comment_footer_item

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as? CommentFooterBean)?.let {
            helper.setText(R.id.unfold, item.text)
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {

    }

}



