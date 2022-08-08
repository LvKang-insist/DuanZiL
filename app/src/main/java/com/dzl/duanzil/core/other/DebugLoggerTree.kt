package com.dzl.duanzil.core.other

import timber.log.Timber.DebugTree
import android.os.Build

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/08/12
 * desc   : 自定义日志打印规则
 */
class DebugLoggerTree : DebugTree() {
    /**
     * 创建日志堆栈 TAG
     */
    override fun createStackElementTag(element: StackTraceElement): String? {
        val tag = "(" + element.fileName + ":" + element.lineNumber + ")"
        // 日志 TAG 长度限制已经在 Android 8.0 被移除
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }

    companion object {
        private const val MAX_TAG_LENGTH = 23
    }
}