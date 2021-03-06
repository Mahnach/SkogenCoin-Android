package com.skogen.coin.screens.main.menu_screen.fragment.presentation.adapters

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import com.skogen.coin.R
import com.skogen.coin.models.MenuMealModel
import com.skogen.coin.skeleton.recycler.BaseRvAdapter
import com.skogen.coin.skeleton.recycler.BaseVH
import kotlinx.android.synthetic.main.item_menu_category.view.*
import com.bumptech.glide.Glide

/**
 * Created by Koba on 22.11.2018.
 * cosmicgate97@gmail.com
 */

open class MenuCategoryAdapter(var context: Context?) : BaseRvAdapter<MenuMealModel, MenuCategoryAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: MenuMealModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseVH<MenuMealModel>(parent, R.layout.item_menu_category) {

        override fun bind(item: MenuMealModel?) {
            Glide.with(context!!).load(item?.photo).into(itemView.itemMenuIvMeal)

            itemView.itemMenuIvMeal.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view!!.width, (view.height + 60), 60f)
                }
            }
            itemView.itemMenuIvMeal.clipToOutline = true

            itemView.itemMenuTvName.text = item?.name
            itemView.itemMenuTvDesc.text = item?.description

            var array: IntArray = IntArray(item?.prices!!.size)
            for (i in item.prices!!.indices) {
                array[i] = item.prices!![i].price!!
            }

            itemView.itemMenuTvPrice.text = context!!.getString(R.string.price_placeholder, array.min())

            // TODO: test info!!
            itemView.itemMenuTvPercents.text = "25% moms"

            itemView.itemMenuTvNext.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(getItem(adapterPosition))
                }
            }
        }
    }
}