package com.dzl.duanzil.ui.home.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.AttentionListBean
import com.dzl.duanzil.databinding.HomeTabItemImgBinding
import com.dzl.duanzil.databinding.HomeTabItemTextBinding
import com.dzl.duanzil.databinding.HomeTabItemVideoBinding
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.GlideAppUtils
import timber.log.Timber

/**
 * @name HomeTabAdapter
 * @package com.dzl.duanzil.ui.home.adapter
 * @author 345 QQ:1831712732
 * @time 2022/08/04 14:48
 * @description
 */
class HomeTabAdapter : BaseProviderMultiAdapter<AttentionListBean.AttentionListBeanItem>() {

    init {
        addItemProvider(ImgItemProvider())
        addItemProvider(TextItemProvider())
        addItemProvider(VideoItemProvider())
    }

    override fun getItemType(
        data: List<AttentionListBean.AttentionListBeanItem>,
        position: Int
    ): Int {
        return data[position].itemType
    }

}


private class TextItemProvider : BaseItemProvider<AttentionListBean.AttentionListBeanItem>() {

    override val itemViewType: Int = 1
    override val layoutId: Int
        get() = R.layout.home_tab_item_text

    override fun convert(helper: BaseViewHolder, item: AttentionListBean.AttentionListBeanItem) {
        DataBindingUtil.bind<HomeTabItemTextBinding>(helper.itemView)?.let {
            it.content.text = item.joke.content
            it.likeCount.text = item.info.likeNum.toString()
            GlideAppUtils.loadImageCircleCrop(context, item.user.avatar, it.avatar)
            it.name.text = item.user.nickName
            it.like.isSelected = item.info.isLike
        }
    }

}

private class ImgItemProvider : BaseItemProvider<AttentionListBean.AttentionListBeanItem>() {

    override val itemViewType: Int = 2
    override val layoutId: Int
        get() = R.layout.home_tab_item_img

    override fun convert(helper: BaseViewHolder, item: AttentionListBean.AttentionListBeanItem) {
        DataBindingUtil.bind<HomeTabItemImgBinding>(helper.itemView)?.let {
            var img = item.joke.imageUrl.split(',')[0]
            img = img.subSequence(6, img.length).toString()
            img = AESUtils.decrypt(img)
            Timber.e(img)
            GlideAppUtils.loadImageRound(context, img, it.image, 16)
            it.content.text = item.joke.content
            it.likeCount.text = item.info.likeNum.toString()
            GlideAppUtils.loadImageCircleCrop(context, item.user.avatar, it.avatar)
            it.name.text = item.user.nickName
            it.like.isSelected = item.info.isLike
        }
    }

}


private class VideoItemProvider : BaseItemProvider<AttentionListBean.AttentionListBeanItem>() {

    override val itemViewType: Int = 3
    override val layoutId: Int
        get() = R.layout.home_tab_item_video

    override fun convert(helper: BaseViewHolder, item: AttentionListBean.AttentionListBeanItem) {
        DataBindingUtil.bind<HomeTabItemVideoBinding>(helper.itemView)?.let {
            var img = item.joke.thumbUrl
            if(img.contains( "ftp://")){
                img = img.subSequence(6, img.length).toString()
                img = AESUtils.decrypt(img)
            }
            GlideAppUtils.loadImageRound(context, img, it.image, 16)
            it.content.text = item.joke.content
            it.likeCount.text = item.info.likeNum.toString()
            GlideAppUtils.loadImageCircleCrop(context, item.user.avatar, it.avatar)
            it.name.text = item.user.nickName
            it.like.isSelected = item.info.isLike
        }
    }

}