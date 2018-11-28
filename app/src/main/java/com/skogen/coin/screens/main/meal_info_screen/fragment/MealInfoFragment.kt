package com.skogen.coin.screens.main.meal_info_screen.fragment

import android.os.Bundle
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.ListPopupWindow
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.skogen.coin.R
import com.skogen.coin.models.MenuMealModel
import com.skogen.coin.models.TypeSizeModel
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.screens.main.meal_info_screen.fragment.presentation.adapter.MealTypeSizeAdapter
import com.skogen.coin.screens.main.meal_info_screen.fragment.presentation.presenter.MealInfoPresenter
import com.skogen.coin.screens.main.meal_info_screen.fragment.presentation.view.MealInfoView
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_meal_info.*
import timber.log.Timber
import android.widget.ArrayAdapter


class MealInfoFragment : BaseFragment<MainActivity, MealInfoPresenter>(), MealInfoView {

    lateinit var rvAdapterSizes: MealTypeSizeAdapter
    lateinit var rvAdapterTypes: MealTypeSizeAdapter
    lateinit var meal: MenuMealModel
    var pickedSize: String = ""
    var pickedType: String = ""
    var quantity: Int = 0

    override val layoutId: Int
        get() = R.layout.fragment_meal_info

    override fun createPresenter(): MealInfoPresenter {
        return MealInfoPresenter(this)
    }

    companion object {
        const val ARG_MEAL = "arg_meal"

        fun newInstance(meal: MenuMealModel): MealInfoFragment {
            val args = Bundle()
            val fragment = MealInfoFragment()
            args.putSerializable(ARG_MEAL, meal)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        meal = arguments?.getSerializable(ARG_MEAL) as MenuMealModel

        Glide.with(this).load(meal.photo).into(mealInfoIvMeal)
        mealInfoTvName.text = meal.name
        initAdapters()

        val popupList : ArrayList<String> = ArrayList()
        popupList.add("1")
        popupList.add("2")
        popupList.add("3")
        popupList.add("4")


        Timber.e("mealInfoTvQuantity.width -> %s", mealInfoTvQuantity.width)
        val mListPopupWindow = ListPopupWindow(context!!, null)
        mListPopupWindow.width = ListPopupWindow.WRAP_CONTENT
        mListPopupWindow.height = ListPopupWindow.WRAP_CONTENT
        mListPopupWindow.anchorView = mealInfoTvQuantity
        mListPopupWindow.setAdapter(ArrayAdapter(context!!,
                R.layout.item_popup_quantity, popupList))

        mListPopupWindow.setOnDismissListener {
            if (quantity == 0)
                mealInfoTvQuantity.setBackgroundResource(R.drawable.shape_bg_stroke_primary_rounded)
        }

        mListPopupWindow.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, int: Int, long: Long) {
                quantity = popupList[int].toInt()
                mealInfoTvQuantityValue.text = quantity.toString()
                mealInfoTvQuantityValue.visibility = View.VISIBLE
                updatePrice()
                mealInfoTvQuantity.setBackgroundResource(R.drawable.shape_bg_stroke_primary_rounded_top)
                mListPopupWindow.dismiss()
            }
        })

        mealInfoTvQuantity.setOnClickListener {
            mealInfoTvQuantity.setBackgroundResource(R.drawable.shape_bg_stroke_primary_rounded_top)
            mListPopupWindow.show()
        }
    }

    private fun initAdapters() {
        rvAdapterSizes = MealTypeSizeAdapter(true)
        rvAdapterSizes.onItemClickListener = object : MealTypeSizeAdapter.OnItemClickListener {
            override fun onItemClick(item: TypeSizeModel, pos: Int) {
                var list = rvAdapterSizes.itemsCopy
                for (size in list) {
                    size.isChecked = size.value.contentEquals(item.value)
                }
                rvAdapterSizes.setItems(list)
                rvAdapterSizes.notifyDataSetChanged()
                pickedSize = item.value
                updatePrice()
            }
        }
        mealInfoRvSizes.layoutManager = LinearLayoutManager(context)
        mealInfoRvSizes.adapter = rvAdapterSizes

        rvAdapterTypes = MealTypeSizeAdapter(false)
        rvAdapterTypes.onItemClickListener = object : MealTypeSizeAdapter.OnItemClickListener {
            override fun onItemClick(item: TypeSizeModel, pos: Int) {
                var list = rvAdapterTypes.itemsCopy
                for (size in list) {
                    size.isChecked = size.value.contentEquals(item.value)
                }
                rvAdapterTypes.setItems(list)
                rvAdapterTypes.notifyDataSetChanged()
                pickedType = item.value
                updatePrice()
            }
        }
        mealInfoRvTypes.layoutManager = LinearLayoutManager(context)
        mealInfoRvTypes.adapter = rvAdapterTypes

        var sizes: ArrayList<TypeSizeModel> = ArrayList()
        var types: ArrayList<TypeSizeModel> = ArrayList()
        for (item in meal.prices!!) {
            if (sizes.isEmpty())
                sizes.add(TypeSizeModel(item.volume!!, false))
            else {
                for (size in sizes) {
                    if (!size.value.contentEquals(item.volume!!))
                        sizes.add(TypeSizeModel(item.volume!!, false))
                }
            }
        }

        for (item in meal.prices!!) {
            if (types.isEmpty())
                types.add(TypeSizeModel(item.type!!, false))
            else {
                for (type in types) {
                    if (!type.value.contentEquals(item.type!!))
                        types.add(TypeSizeModel(item.type!!, false))
                }
            }
        }
        rvAdapterSizes.setItems(sizes)
        rvAdapterTypes.setItems(types)
    }

    private fun updatePrice() {
        var filterList = meal.prices!!.filter { it.volume == pickedSize && it.type == pickedType }
        if (filterList.isNotEmpty()) {
            for (item in filterList) {
                if (quantity == 0) {
                    quantity = 1
                    mealInfoTvQuantityValue.text = quantity.toString()
                    mealInfoTvQuantityValue.visibility = View.VISIBLE
                    mealInfoTvQuantity.setBackgroundResource(R.drawable.shape_bg_stroke_primary_rounded_top)
                }
                mealInfoTvPrice.text = getString(R.string.mealInfoTvPricePlaceHolder, item.price!! * quantity)
            }
        } else mealInfoTvPrice.text = getString(R.string.mealInfoTvPriceDefault)
    }
}
