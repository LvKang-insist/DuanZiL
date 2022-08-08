package com.dzl.duanzil.state.home

import com.dzl.duanzil.bean.AttentionListBean
import com.dzl.duanzil.state.ListStatus

/**
 * @name HomeTabViewState
 * @package com.dzl.duanzil.state.home
 * @author 345 QQ:1831712732
 * @time 2022/08/03 21:15
 * @description
 */
data class HomeTabViewState(
    var state: ListStatus,
    var list: List<AttentionListBean.AttentionListBeanItem>? = null
)


sealed class HomeTabViewAction() {

    data class Type(val type: Int) : HomeTabViewAction()

    object RefreshList : HomeTabViewAction()

    data class LoadMore(val page: Int) : HomeTabViewAction()

}