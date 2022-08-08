package com.dzl.duanzil.extension

import com.dzl.duanzil.core.net.api.HomeApi
import com.lvhttp.net.LvHttp

/**
 * @name ApiExt
 * @package com.dzl.duanzil.extension
 * @author 345 QQ:1831712732
 * @time 2022/08/02 22:03
 * @description
 */


/** 首页 Api */
val homeApi by lazy { LvHttp.createApi(HomeApi::class.java) }
