package com.dzl.duanzil.extension

import com.tencent.mmkv.MMKV

/**
 * @name MMkvKtx
 * @package com.dzl.duanzil.extension
 * @author 345 QQ:1831712732
 * @time 2022/08/02 21:41
 * @description
 */
enum class MMkvEnum {

    TOKEN,

}

object MMkvKtx{
    val MK: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    fun clearAll() {
        MK.clearAll()
    }
}

/** 添加你的value */
fun MMkvEnum.putString(value: String) {
    MMkvKtx.MK.encode(this.name, value)
}

fun MMkvEnum.putBoolean(value: Boolean) {
    MMkvKtx.MK.encode(this.name, value)
}

fun MMkvEnum.putInt(value: Int) {
    MMkvKtx.MK.encode(this.name, value)
}

fun MMkvEnum.putLong(value: Long) {
    MMkvKtx.MK.encode(this.name, value)
}
//更多自行添加

fun MMkvEnum.getBoolean(default: Boolean = false): Boolean {
    return MMkvKtx.MK.decodeBool(this.name, default)
}

fun MMkvEnum.getInt(default: Int = -1): Int {
    return MMkvKtx.MK.decodeInt(this.name, default)
}

fun MMkvEnum.getString(default: String = ""): String {
    return MMkvKtx.MK.decodeString(this.name, default) ?: ""
}

fun MMkvEnum.getLong(default: Long = 0): Long {
    return MMkvKtx.MK.decodeLong(this.name, default)
}