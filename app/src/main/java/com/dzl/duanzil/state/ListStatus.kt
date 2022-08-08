package com.dzl.duanzil.state

/**
 * @name ListStatus
 * @package com.dzl.duanzil.state
 * @author 345 QQ:1831712732
 * @time 2022/08/04 10:52
 * @description
 */
sealed class ListStatus {

    object RefreshSuccessStatus : ListStatus()
    object LoadMoreSuccessStatus : ListStatus()
}