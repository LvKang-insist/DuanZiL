package com.dzl.duanzil.utils

/**
 * @name AesUtils
 * @package com.dzl.duanzil.utils
 * @author 345 QQ:1831712732
 * @time 2022/08/04 18:15
 * @description 视频和图片URL处理：
1、拿到链接后 先去除掉头部的 ftp://
2、AES对称加密，密钥临时为cretinzp**273846
3、剩下的内容再解密
 */
import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * AES加密工具类：128位 不带偏移量
 *
 * @author lilinshen
 * @date 2021/03/31
 */

/**
 *
 * 此处使用AES-128-ECB加密模式，key需要为16位
 *
 */
object AESUtils {
    // 加密

    fun encrypt(sSrc: String, sKey: String): String? {
        val raw = sKey.toByteArray(charset("utf-8"))
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") //"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val encrypted = cipher.doFinal(sSrc.toByteArray(charset("utf-8")))
        return android.util.Base64.encodeToString(encrypted, 0) //此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    val raw = "cretinzp**273846".toByteArray(charset("utf-8"))
    val skeySpec = SecretKeySpec(raw, "AES")
    @SuppressLint("GetInstance")
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding").apply {
        init(Cipher.DECRYPT_MODE, skeySpec)
    }

    // 解密
    fun decrypt(sSrc: String): String {
        return try {


            val encrypted1: ByteArray = android.util.Base64.decode(sSrc, 0) //先用base64解密
            try {
                val original = cipher.doFinal(encrypted1)
                String(original)
            } catch (e: Exception) {
                println(e.toString())
                ""
            }
        } catch (ex: Exception) {
            println(ex.toString())
            ""
        }
    }


}