package com.dzl.duanzil.ui.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.dzl.duanzil.R

/**
 * @name MainNavigationAdapter
 * @package com.dzl.duanzil.ui.adapter
 * @author 345 QQ:1831712732
 * @time 2022/08/01 22:27
 * @description
 */
class MainNavigationAdapter(private val menuItem: List<MenuItem>) :
    RecyclerView.Adapter<MainNavigationAdapter.MainNavigationHolder>() {

    var listener: MainNavigationItemClickListener? = null

    var selectPosition: Int = 0


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainNavigationHolder {
        val holder = MainNavigationHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_navigation_item, parent, false)
        )
        holder.itemView.setOnClickListener {
            selectPosition = holder.adapterPosition
            notifyDataSetChanged()
            listener?.onClick(selectPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MainNavigationHolder, position: Int) {

        holder.setData(menuItem[position], position)
    }

    override fun getItemCount(): Int = menuItem.size


    inner class MainNavigationHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconView: AppCompatImageView by lazy { view.findViewById<AppCompatImageView>(R.id.iv_main_navigation_icon) }
        private val titleView by lazy { view.findViewById<AppCompatTextView>(R.id.tv_main_navigation_title) }
        fun setData(item: MenuItem, pos: Int) {
            iconView.setImageDrawable(item.res)
            titleView.text = item.text
            val flag = selectPosition == pos
            iconView.isSelected = flag
            if (flag) {
                setTextColor(titleView, R.color.textColor)
            } else {
                setTextColor(titleView, R.color.main_nav_off)
            }
        }
    }

    private fun setTextColor(textView: AppCompatTextView, colorRes: Int) {
        textView.setTextColor(ResourcesCompat.getColor(textView.resources, colorRes, null))
    }

    class MenuItem(val text: String, val res: Drawable)
}

interface MainNavigationItemClickListener {
    fun onClick(position: Int)
}