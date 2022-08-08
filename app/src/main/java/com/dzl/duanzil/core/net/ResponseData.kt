package com.dzl.duanzil.core.net

import com.lvhttp.net.response.BaseResponse

/**
 * @name ResponseData
 * @package com.dzl.duanzil.core.net
 * @author 345 QQ:1831712732
 * @time 2022/08/02 19:10
 * @description
 */
class ResponseData<T>(val data: T, val code: Int, val msg: String) : BaseResponse<T>() {

    override fun notifyData(): BaseResponse<T> {
        _data = data
        _code = code
        _message = msg
        return super.notifyData()
    }

}