package com.dzl.duanzil.ui.release

import android.util.Log
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragReleaseBinding

/**
 * @name HomeFragment
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:36
 * @description 发布
 */
class ReleaseFragment : BaseBindingFragment<FragReleaseBinding>() {

    override fun getLayoutId(): Int = R.layout.frag_release

    override fun initView() {
        Log.e("---345--->", "Release");

    }

}