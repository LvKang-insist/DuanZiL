package com.dzl.duanzil.ui.jokes


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.btpj.lib_base.utils.DateUtil
import com.dzl.duanzil.R
import com.dzl.duanzil.bean.*
import com.dzl.duanzil.core.base.BaseBindingActivity
import com.dzl.duanzil.core.other.AdapterHelper
import com.dzl.duanzil.databinding.ActivityJokesDetailBinding
import com.dzl.duanzil.databinding.JokesDetailVideoBinding
import com.dzl.duanzil.extension.MMkvEnum
import com.dzl.duanzil.extension.getString
import com.dzl.duanzil.extension.setRadius
import com.dzl.duanzil.ui.adapter.JokeCommentAdapter
import com.dzl.duanzil.utils.AESUtils
import com.dzl.duanzil.utils.GlideAppUtils
import com.dzl.duanzil.utils.ScreenUtil
import com.dzl.duanzil.viewmodel.JokesDetailViewModel
import com.dzl.duanzil.viewmodel.JokesIntent
import com.dzl.duanzil.viewmodel.JokesUIState
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import timber.log.Timber

class JokesDetailActivity : BaseBindingActivity<ActivityJokesDetailBinding>() {


    val jokeBean by lazy { intent.getSerializableExtra("jokeBean") as JokeListBean.JokeListBeanItem }

    val viewModel by viewModels<JokesDetailViewModel>()

    private val imageView by lazy {
        AppCompatImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply {
                maxHeight = ScreenUtil.dp2px(this@JokesDetailActivity, 500f)
                gravity = Gravity.CENTER
            }
        }
    }

    private val playerBinding by lazy {
        DataBindingUtil.bind<JokesDetailVideoBinding>(
            layoutInflater.inflate(R.layout.jokes_detail_video, binding.layout, false)
        )
    }

    val orientationUtils by lazy {
        OrientationUtils(
            this@JokesDetailActivity,
            playerBinding?.player
        )
    }

    private val adapter = JokeCommentAdapter()
    private val adapterHelper by lazy {
        AdapterHelper(binding.refresh, isRefresh = false, isLoadMore = true, loadRefresh = {
            viewModel.dispatch(JokesIntent.LoadMoreComment(it.page))
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_jokes_detail

    override fun initView() {
        //168510
        Timber.e("jokeId :${jokeBean.joke.jokesId}   ${MMkvEnum.TOKEN.getString()}")
        binding.recycler.adapter = adapter
        viewModel.dispatch(JokesIntent.JokesId(168510))
        viewModel.dispatch(JokesIntent.RefreshComment)
    }

    private fun initImg() {
        val url = AESUtils.decryptImg(jokeBean.joke.imageUrl)
        GlideAppUtils.loadImage(this, url, imageView)
        binding.layout.addView(imageView)
    }

    private fun initVideo() {
        var videoH = LinearLayout.LayoutParams.WRAP_CONTENT
        val videoW = LinearLayout.LayoutParams.MATCH_PARENT

        if (jokeBean.joke.videoSize.contains(',')) {
            val size = jokeBean.joke.videoSize.split(',')
            val w = size[0].toFloat()
            val h = size[1].toFloat()
            val s = ScreenUtil.getScreenWidth(this) / w
            videoH = (h * s).toInt()
        }

        playerBinding?.run {
            val url = AESUtils.decryptImg(jokeBean.joke.videoUrl)
            val thumbUrl = AESUtils.decryptImg(jokeBean.joke.thumbUrl)
            Timber.e("-- ${jokeBean.joke.videoSize} -------- $url")
            player.layoutParams = ConstraintLayout.LayoutParams(videoW, videoH)
            val thumbImage = AppCompatImageView(this@JokesDetailActivity).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            GlideAppUtils.loadImage(this@JokesDetailActivity, thumbUrl, thumbImage)
            val gsyVideoOption = GSYVideoOptionBuilder()
            gsyVideoOption.setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setLooping(true)
                .setNeedLockFull(true)
                .setUrl(url)
                .setThumbImageView(thumbImage)
                .setVideoTitle(jokeBean.user.nickName)
                .setCacheWithPlay(false)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any?) {
                        //开始播放了才能旋转和全屏
                        orientationUtils.isEnable = true;
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                        orientationUtils.backToProtVideo();
                    }
                })
                .setLockClickListener { _, lock ->
                    //配合下方的onConfigurationChanged
                    orientationUtils.isEnable = !lock
                }
                .build(player)
            player.fullscreenButton.setOnClickListener {
                orientationUtils.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                player.startWindowFullscreen(this@JokesDetailActivity, true, true);
            }
            player.startPlayLogic()
            binding.layout.addView(playerBinding?.root)
        }

    }

    override fun listener() {
        adapter.addChildClickViewIds(R.id.unfold, R.id.unfold_more, R.id.isOpen)
        adapter.setOnItemChildClickListener { _, view, position ->
            val node = adapter.data[position]
            when (view.id) {
                R.id.unfold -> {
                    (node as? CommentFooterBean)?.let { moreComment(it.commentId, position) }
                }
                R.id.unfold_more -> {
                    (node as? CommentFooterMoreBean)?.let { moreComment(it.commentId, position) }
                }
                R.id.isOpen -> {
                    (node as? CommentFooterIsOpen)?.let { expanded(it.commentId, position) }
                }
            }
        }

        viewModel.state.observe(this, Observer {
            when (it) {
                is JokesUIState.LoadMoreComment -> {
                    adapterHelper.run { adapter.loadData(it.comment.comments) }
                }
                is JokesUIState.RefreshComment -> {
                    adapterHelper.run { adapter.refreshData(it.comment.comments) }
                }
                is JokesUIState.LoadMoreChildComment -> {
                    (adapter.data[it.parentPos] as? CommentListBean.Comment)?.let { bean ->
                        setChildLoadComment(bean, it)
                    }
                }
            }
        })
    }

    private fun expanded(commentId: Int, position: Int) {
        var parPos = -1
        for (i in 0 until adapter.data.size) {
            (adapter.data[i] as? CommentListBean.Comment)?.let {
                if (it.commentId == commentId) parPos = i
            }
            if (parPos != -1) break
        }
        if (parPos != -1) {
            val node = adapter.data[parPos]
            (node as? CommentListBean.Comment)?.let {
                if (it.isExpanded) {
                    adapter.data[position] = CommentFooterIsOpen(commentId, "展开")
                    adapter.notifyItemChanged(position)
                    adapter.collapseAndChild(parPos)
                } else {
                    adapter.data[position] = CommentFooterIsOpen(commentId, "收起")
                    adapter.notifyItemChanged(position)
                    adapter.expandAndChild(parPos)
                }
            }
        }
    }

    /** 设置子评论数据 */
    private fun setChildLoadComment(
        bean: CommentListBean.Comment,
        state: JokesUIState.LoadMoreChildComment
    ) {
        //评论数<总数
        if ((bean.childNode?.size ?: (0 + state.comment.size)) < bean.itemCommentNum) {
            adapter.data[state.curPos] = CommentFooterMoreBean(bean.commentId)
        } else {
            adapter.data[state.curPos] = CommentFooterIsOpen(bean.commentId, "收起")
        }
        adapter.notifyItemChanged(state.curPos)
        adapter.nodeAddData(bean, bean.itemCommentList.size, state.comment)
    }

    private fun moreComment(commentId: Int, position: Int) {
        var parPos = -1
        for (i in 0 until adapter.data.size) {
            (adapter.data[i] as? CommentListBean.Comment)?.let {
                if (it.commentId == commentId) parPos = i
            }
            if (parPos != -1) break
        }
        (adapter.data[parPos] as? CommentListBean.Comment)?.run {
            adapter.data[position] = CommentFooterRefreshBean()
            adapter.notifyItemChanged(position)
            viewModel.dispatch(
                JokesIntent.LoadMoreChildComment(this.page, commentId, parPos, position)
            )
            this.page++
        }
    }


    @SuppressLint("SetTextI18n")
    override fun initData() {

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
        binding.time.text =
            "${DateUtil.getTimeFromNow(jokeBean.joke.addTime)} ${jokeBean.joke.showAddress}"

        binding.layout.removeAllViews()
        when (jokeBean.joke.type) {
            1 -> {}
            2 -> initImg()
            else -> initVideo()
        }

        GlideAppUtils.loadImageCircleCrop(this, jokeBean.user.avatar, binding.layoutComment.avatar)
        binding.layoutComment.commentCount.text = "共 ${jokeBean.info.commentNum} 条评论"
        binding.layoutComment.comment.setRadius(ScreenUtil.dp2px(this, 16f))
    }

    override fun onBackPressed() {
        if (jokeBean.joke.type >= 3) {
            orientationUtils.backToProtVideo()
            if (GSYVideoManager.backFromWindowFull(this)) return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        if (jokeBean.joke.type >= 3) {
            playerBinding?.player?.currentPlayer?.onVideoPause()
        }
        super.onPause()
    }

    override fun onResume() {
        if (jokeBean.joke.type >= 3) {
            playerBinding?.player?.currentPlayer?.onVideoResume(false)
        }
        super.onResume()
    }

    override fun onDestroy() {
        if (jokeBean.joke.type >= 3) {
            playerBinding?.player?.currentPlayer?.release()
            orientationUtils.releaseListener()
        }
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (jokeBean.joke.type >= 3) {
            playerBinding?.player?.onConfigurationChanged(
                this,
                newConfig,
                orientationUtils,
                true,
                true
            );
        }
    }
}