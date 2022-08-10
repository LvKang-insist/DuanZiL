package com.dzl.duanzil.ui.jokes


import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.JokeListBean
import com.dzl.duanzil.core.base.BaseBindingActivity
import com.dzl.duanzil.databinding.ActivityJokesDetailBinding
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.GlideAppUtils
import com.dzl.duanzil.viewmodel.JokesDetailViewModel
import com.dzl.duanzil.viewmodel.JokesIntent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class JokesDetailActivity : BaseBindingActivity<ActivityJokesDetailBinding>() {


    val jokeBean by lazy { intent.getSerializableExtra("jokeBean") as JokeListBean.JokeListBeanItem }

    val viewModel by viewModels<JokesDetailViewModel>()

    private val imageView by lazy {
        ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_jokes_detail

    override fun initView() {
        viewModel.dispatch(JokesIntent.JokesId(jokeBean.joke.jokesId))
        viewModel.dispatch(JokesIntent.RefreshComment)
    }

    override fun initData() {

        lifecycleScope.launch(CoroutineExceptionHandler { c, t -> } + SupervisorJob()) {

        }

        binding.toolbar.name.text = jokeBean.user.nickName
        GlideAppUtils.loadImageCircleCrop(this, jokeBean.user.avatar, binding.toolbar.avatar)
        if (jokeBean.info.isAttention) {
            binding.toolbar.attention.run {
                text = "已关注"
                setBackgroundResource(R.drawable.shape_primary_small_off)
            }
        } else {
            binding.toolbar.attention.run {
                text = "关注"
                setBackgroundResource(R.drawable.shape_primary_small_on)
            }
        }
        binding.toolbar.address.text =
            jokeBean.user.signature.ifEmpty { "该用户暂无签名哟~~~" }
        binding.content.text = jokeBean.joke.content
        binding.time.text = jokeBean.joke.addTime

        binding.layout.removeAllViews()
        when (jokeBean.joke.type) {
            2 -> {
                val url = AESUtils.decryptImg(jokeBean.joke.imageUrl)
                Timber.e("${jokeBean.joke.imageSize} --- ${jokeBean.joke.imageUrl}")
                Timber.e("${jokeBean.joke.imageSize} --- $url")
                GlideAppUtils.loadImageMxHeight(this, url, imageView)
                binding.layout.addView(imageView)
            }
        }

    }

}