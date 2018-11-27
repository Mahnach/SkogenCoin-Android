package com.skogen.coin.screens.main.meal_info_screen.fragment

import android.os.Bundle
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
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

class MealInfoFragment : BaseFragment<MainActivity, MealInfoPresenter>(), MealInfoView {

    lateinit var rvAdapterSizes: MealTypeSizeAdapter
    lateinit var rvAdapterTypes: MealTypeSizeAdapter
    lateinit var meal: MenuMealModel
    var pickedSize: String = ""
    var pickedType: String = ""

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

        // TODO: fix popup menu style!!!
        val wrapper = ContextThemeWrapper(context!!, R.style.PopupMenuQuantityItemStyle)
        val popupMenu = PopupMenu(wrapper, mealInfoTvQuantity)

        popupMenu.setOnMenuItemClickListener {
            Timber.e("picked -> %s", it.title)
            true
        }
        popupMenu.menu.add("1")
        popupMenu.menu.add("2")
        popupMenu.menu.add("3")
        popupMenu.menu.add("4")

        mealInfoTvQuantity.setOnClickListener {
            popupMenu.show()
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
                Timber.e("Price -> %s", item.price)
                mealInfoTvPrice.text = getString(R.string.mealInfoTvPricePlaceHolder, item.price)
            }
        } else mealInfoTvPrice.text = getString(R.string.mealInfoTvPriceDefault)
    }
}
