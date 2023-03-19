package com.dzl.duanzil.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragVideoTabItemBinding
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.GlideUtil

/**
 * @name VideoTabFragment
 * @package com.dzl.duanzil.ui.video
 * @author 345 QQ:1831712732
 * @time 2022/09/02 17:25
 * @description
 */
class VideoTabFragment : BaseBindingFragment<FragVideoTabItemBinding>() {


    val bean by lazy { arguments?.getSerializable("bean") as VideoListBean.VideoListBeanItem }
    val position by lazy { arguments?.getInt("position") ?: -1 }
    val url by lazy { AESUtils.decryptImg(bean.joke.videoUrl) }
    val thumbUrl by lazy { AESUtils.decryptImg(bean.joke.thumbUrl) }


    var seekOnStart = 0L

    override fun getLayoutId(): Int = R.layout.frag_video_tab_item

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    @SuppressLint("SetTextI18n")
    override fun initView() {

        binding.name.isSelected = true
        binding.name.text = "@${bean.user.nickName} 发布的视频"
        binding.content.text = bean.joke.content

        GlideUtil.loadImageCropCircleWithBorder(
            requireContext(),
            bean.user.avatar,
            binding.avatar,
            5,
            ResourcesCompat.getColor(resources, R.color.white, null)
        )
    }



}