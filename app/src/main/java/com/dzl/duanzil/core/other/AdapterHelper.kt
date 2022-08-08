package com.dzl.duanzil.core.other

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @name AdapterExt
 * @package com.genuine.cloud.extension
 * @author 345 QQ:1831712732
 * @time 2021/07/23 13:38
 * @description
 */
class AdapterHelper(
    private val smartRefresh: SmartRefreshLayout,
    private val refresh: ((AdapterHelper) -> Unit)? = null,
    private val loadRefresh: ((AdapterHelper) -> Unit)? = null,
    private val isLoadMore: Boolean = true,
    private val enableRefresh: Boolean = true,
) {
    companion object {
        const val LIST_PAGE_SIZE = 20
    }

    var page: Int = 1

    /** 当前页数量 */
    private var curPageCount = 0

    init {
        smartRefresh.setEnableRefresh(enableRefresh)
        smartRefresh.setEnableLoadMore(isLoadMore)
        smartRefresh.setOnRefreshListener {
            refresh?.invoke(this)
        }
        if (isLoadMore) {
            smartRefresh.setOnLoadMoreListener {
//                if (curPageCount >= LIST_PAGE_SIZE) {
                    loadRefresh?.invoke(this)
//                } else {
//                    smartRefresh.finishLoadMoreWithNoMoreData()
//                }
            }
        }
    }


    /** 下拉刷新 */
    fun autoRefresh() {
        if (!smartRefresh.autoRefresh()) refresh()
    }

    /** 直接刷新 */
    fun refresh() {
        refresh?.invoke(this)
    }

    fun finishRefresh() {
        smartRefresh.finishRefresh()
    }

    fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.refreshData(pageData: Collection<T>?) {
        page = 1
        pageData?.run {
            setNewInstance(pageData.toMutableList())
            if (data.isEmpty() && isLoadMore) setEmpty()
            smartRefresh.finishRefresh()
        } ?: kotlin.run {
            if (data.isEmpty() && isLoadMore) setEmpty()
            smartRefresh.finishRefresh()
        }
    }

    fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.loadData(pageData: Collection<T>?) {
        pageData?.run {
            ++page
            curPageCount = pageData.size
            addData(pageData)
            smartRefresh.finishLoadMore(true)
        } ?: kotlin.run {
            smartRefresh.finishLoadMore(false)
        }
    }

    fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.setEmpty() {
        if (data.isEmpty()) {
//            setEmptyView(R.layout.layout_empty_data)
        }
    }

}



