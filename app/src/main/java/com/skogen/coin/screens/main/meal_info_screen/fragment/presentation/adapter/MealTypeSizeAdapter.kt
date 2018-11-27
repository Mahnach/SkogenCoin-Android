package com.skogen.coin.screens.main.meal_info_screen.fragment.presentation.adapter

import android.graphics.Typeface
import android.view.ViewGroup
import com.skogen.coin.R
import com.skogen.coin.models.TypeSizeModel
import com.skogen.coin.skeleton.recycler.BaseRvAdapter
import com.skogen.coin.skeleton.recycler.BaseVH
import kotlinx.android.synthetic.main.item_meal_type_size.view.*


/**
 * Created by Koba on 27.11.2018.
 * cosmicgate97@gmail.com
 */

class MealTypeSizeAdapter(var isSizes: Boolean) : BaseRvAdapter<TypeSizeModel, MealTypeSizeAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: TypeSizeModel, pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseVH<TypeSizeModel>(parent, R.layout.item_meal_type_size) {

        override fun bind(item: TypeSizeModel?) {
            itemView.itemMealTypeSizeTv.text = item?.value

//                    if (isSizes)
//                item?.value?.volume
//            else
//                item?.value?.type

            setChecked(item?.isChecked!!)

            itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(getItem(adapterPosition), adapterPosition)
                }
            }
        }

        private fun setChecked(checked: Boolean) {
            if (checked)
                itemView.itemMealTypeSizeTv.setTypeface(itemView.itemMealTypeSizeTv.typeface, Typeface.BOLD)
            else itemView.itemMealTypeSizeTv.typeface = Typeface.DEFAULT
        }
    }
}