package com.dzl.duanzil.ui.jokes


import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingActivity
import com.dzl.duanzil.databinding.ActivityJokesDetailBinding

class JokesDetailActivity : BaseBindingActivity<ActivityJokesDetailBinding>() {

    val id by lazy { intent.getIntExtra("id", -1) }

    override fun getLayoutId(): Int = R.layout.activity_jokes_detail

    override fun initView() {

    }

}