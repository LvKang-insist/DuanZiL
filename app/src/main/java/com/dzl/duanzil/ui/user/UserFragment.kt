package com.dzl.duanzil.ui.user

import android.util.Log
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingFragment
import com.dzl.duanzil.databinding.FragUserBinding

/**
 * @name HomeFragment
 * @package com.dzl.duanzil.ui.home
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:36
 * @description 消息
 */
class UserFragment : BaseBindingFragment<FragUserBinding>() {

    override fun getLayoutId(): Int = R.layout.frag_user

    override fun initView() {
        Log.e("---345--->", "User");

    }

}