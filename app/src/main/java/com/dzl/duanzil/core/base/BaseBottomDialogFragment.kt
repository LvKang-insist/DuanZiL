package com.dzl.duanzil.core.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.fragment.app.DialogFragment

import com.dzl.duanzil.R
import com.dzl.duanzil.utils.ScreenUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/** 通用的底部baseBottomDialogFragment */
abstract class BaseBottomDialogFragment(
    @LayoutRes private val contentLayoutId: Int = 0
) : DialogFragment(contentLayoutId) {

    protected var mBehavior: BottomSheetBehavior<FrameLayout>? = null

    open fun contentHeight(): Int = toFixedHeight(DEFAULT_TOP_OFFSET_HEIGHT)
    open fun contentWidth(): Int = MATCH_HEIGHT

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCanceledOnTouchOutside(true)
            (this as? BottomSheetDialog)?.delegate?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                ?.apply {
                    (layoutParams as? LayoutParams)?.apply {
                        height = contentHeight()
                        width = contentWidth()
                    }
                    mBehavior = BottomSheetBehavior.from(this).apply {
                        peekHeight = 0
                        addBottomSheetCallback(onBottomSheetCallback())
                        state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
        }
    }

    open fun onBottomSheetCallback(): BottomSheetBehavior.BottomSheetCallback =
        object : BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(view: View, newState: Int) {
                defaultSheetChanged(view, newState)
            }

            override fun onSlide(view: View, f: Float) {
                defaultSheetSlide(view, f)
            }
        }

    protected open fun defaultSheetChanged(view: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                dismissAllowingStateLoss()
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                // 展开状态,目前用不到
            }
            // 隐藏
            BottomSheetBehavior.STATE_HIDDEN -> {
                dismissAllowingStateLoss()
            }
        }
    }

    protected open fun defaultSheetSlide(view: View, f: Float) {
        updateBackDimAmount(0.5f * f)
        if (f == 0F) {
            dismissAllowingStateLoss()
        }
    }

    protected open fun updateBackDimAmount(f: Float) {
        dialog?.window?.setDimAmount(f)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(
            requireContext(),
            R.style.BottomDialogTheme
        ).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            window?.setWindowAnimations(R.style.FragmentAnimationSlidFromBottom)
        }
    }

    companion object {
        private const val DEFAULT_TOP_OFFSET_HEIGHT = 0.15F
        const val WRAP_HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT
        const val MATCH_HEIGHT = ViewGroup.LayoutParams.MATCH_PARENT

        fun toFixedHeight(topHeightWidget: Float) =
            (ScreenUtil.screenHeight * 1.0 * (1f - topHeightWidget)).toInt()
    }
}
