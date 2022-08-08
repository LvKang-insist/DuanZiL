package com.dzl.duanzil.repository

import com.dzl.duanzil.bean.AttentionRecommendBean
import com.dzl.duanzil.core.net.ResponseData
import com.dzl.duanzil.extension.homeApi

/**
 * @name HomeRepository
 * @package com.dzl.duanzil.repository
 * @author 345 QQ:1831712732
 * @time 2022/08/03 18:08
 * @description
 */

object HomeRepository {

    suspend fun homeAttentionRecommend(): ResponseData<AttentionRecommendBean> {
        return homeApi.homeAttentionRecommend()
    }
}