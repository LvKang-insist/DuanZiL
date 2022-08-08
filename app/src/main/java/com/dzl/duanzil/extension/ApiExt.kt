package com.dzl.duanzil.extension

import com.dzl.duanzil.core.net.api.HomeApi
import com.dzl.duanzil.core.net.api.JokesApi
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

/** 段子相关接口 */
val jokesApi by lazy { LvHttp.createApi(JokesApi::class.java) }
