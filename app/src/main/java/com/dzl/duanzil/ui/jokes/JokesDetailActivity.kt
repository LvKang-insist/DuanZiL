package com.dzl.duanzil.ui.jokes


import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
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
import com.dzl.duanzil.utils.GlideUtil
import com.dzl.duanzil.utils.ScreenUtil
import com.dzl.duanzil.viewmodel.JokesDetailViewModel
import com.dzl.duanzil.viewmodel.JokesIntent
import com.dzl.duanzil.viewmodel.JokesUIState
import timber.log.Timber
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.*
import xyz.doikki.videoplayer.player.VideoView

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
        viewModel.dispatch(JokesIntent.JokesId(jokeBean.joke.jokesId))
        viewModel.dispatch(JokesIntent.RefreshComment)
    }

    private fun initImg() {
        val url = AESUtils.decryptImg(jokeBean.joke.imageUrl)
        GlideUtil.loadImage(this, url, imageView)
        binding.layout.addView(imageView)
    }

    private fun initVideo() {
        var videoH = LinearLayout.LayoutParams.WRAP_CONTENT
        val videoW = LinearLayout.LayoutParams.MATCH_PARENT

        if (jokeBean.joke.videoSize.contains(',')) {
            val size = jokeBean.joke.videoSize.split(',')
            val w = size[0].toFloat()
            val h = size[1].toFloat()
            val s = ScreenUtil.screenWidth / w
            videoH = (h * s).toInt()
        }

        playerBinding?.run {
            videoView.layoutParams = LinearLayout.LayoutParams(videoW, videoH)
            val url = AESUtils.decryptImg(jokeBean.joke.videoUrl)
            setPlayer(videoView, url)
            binding.layout.addView(playerBinding?.root)
        }

    }

    private fun setPlayer(videoView: VideoView, url: String) {
        val controller = StandardVideoController(this)
        controller.setEnableOrientation(false)
        //准备播放界面
        val prepareView = PrepareView(this)
        prepareView.setClickStart()
        val thumb = prepareView.findViewById<ImageView>(xyz.doikki.videocontroller.R.id.thumb)
        val thumbUrl = AESUtils.decryptImg(jokeBean.joke.thumbUrl)
        GlideUtil.loadImage(this, thumbUrl, thumb)
        controller.addControlComponent(prepareView)
        //自动完成播放界面
        controller.addControlComponent(CompleteView(this))
        //错误界面
        controller.addControlComponent(ErrorView(this))
        //标题
        val titleView = TitleView(this)
        titleView.setTitle(jokeBean.joke.imageUrl)
        controller.addControlComponent(titleView)
        //控制条
        val vodControllerView = VodControlView(this)
//        vodControllerView.showBottomProgress(false)
        controller.addControlComponent(vodControllerView)
        //手势控制
        val gestureController = GestureView(this)
        controller.addControlComponent(gestureController)
        //设置控制器
        videoView.setVideoController(controller)
        videoView.setUrl(url)
        videoView.setLooping(true)
        videoView.start()
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
        GlideUtil.loadCircleImage(this, jokeBean.user.avatar, binding.toolbar.avatar)
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

        GlideUtil.loadCircleImage(this, jokeBean.user.avatar, binding.layoutComment.avatar)
        binding.layoutComment.commentCount.text = "共 ${jokeBean.info.commentNum} 条评论"
        binding.layoutComment.comment.setRadius(ScreenUtil.dp2px(this, 16f))
    }

    override fun onBackPressed() {
        if (jokeBean.joke.type >= 3) {
            if (playerBinding?.videoView == null || !playerBinding!!.videoView.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        if (jokeBean.joke.type >= 3) {
            //正在缓冲时，释放资源
            if (playerBinding?.videoView?.currentPlayState == VideoView.STATE_PREPARING) {
                playerBinding?.videoView?.release()
            } else {
                playerBinding?.videoView?.pause()
            }
        }
        super.onPause()
    }

    override fun onResume() {
        if (jokeBean.joke.type >= 3) {
            playerBinding?.videoView?.resume()
        }
        super.onResume()
    }

    override fun onDestroy() {
        if (jokeBean.joke.type >= 3) {
            playerBinding?.videoView?.release()
        }
        super.onDestroy()
    }

}