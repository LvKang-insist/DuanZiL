package com.dzl.duanzil.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.dzl.duanzil.bean.JokeListBean
import com.dzl.duanzil.ui.jokes.JokesDetailActivity
import com.dzl.duanzil.ui.login.LoginActivity
import com.dzl.duanzil.ui.release.ReleaseActivity

/**
 * @name JumpUtils
 * @package com.dzl.duanzil.utils
 * @author 345 QQ:1831712732
 * @time 2022/08/09 18:17
 * @description
 */
object JumpUtils {


    fun startLogin(context: Context) {
        startActivity(context, LoginActivity::class.java)
    }


    fun startReleaseActivity(activity: AppCompatActivity) {
        activity.applyPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
        ) {
            startActivity(activity, ReleaseActivity::class.java)
        }
    }

    /** 段子详情 */
    fun startHomeDetail(context: Context, jokeBean: JokeListBean.JokeListBeanItem) {
        Intent(context, JokesDetailActivity::class.java).run {
            putExtra("jokeBean", jokeBean)
            startActivity(context, this)
        }
    }


    private fun startActivity(context: Context, activity: Class<*>) {
        context.startActivity(Intent(context, activity))
    }

    private fun startActivity(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

}