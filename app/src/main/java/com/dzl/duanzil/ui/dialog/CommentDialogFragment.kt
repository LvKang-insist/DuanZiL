package com.dzl.duanzil.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBottomDialogFragment
import com.dzl.duanzil.databinding.BottomDialogCommentBinding

/**
 * @name CommentDialogFragment
 * @package com.dzl.duanzil.ui.dialog
 * @author 345 QQ:1831712732
 * @time 2023/03/22 16:36
 * @description
 */
class CommentDialogFragment : BaseBottomDialogFragment() {

    lateinit var binding: BottomDialogCommentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = super.onCreateView(inflater, container, savedInstanceState)
        if (view == null) {
            view = inflater.inflate(R.layout.bottom_dialog_comment, container, false)
            binding = BottomDialogCommentBinding.bind(view)
        }
        return view
    }

}