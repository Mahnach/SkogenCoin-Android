package com.skogen.coin.screens.main.home_screen.fragment

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import com.bumptech.glide.Glide
import com.skogen.coin.App
import com.skogen.coin.R
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.screens.main.home_screen.fragment.presentation.presenter.HomePresenter
import com.skogen.coin.screens.main.home_screen.fragment.presentation.view.HomeView
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_menu_category.view.*

class HomeFragment : BaseFragment<MainActivity, HomePresenter>(), HomeView {

    override val layoutId: Int
        get() = R.layout.fragment_home


    override fun createPresenter(): HomePresenter {
        return HomePresenter(this)
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        // TODO: fix it!!!  
        Glide.with(context!!).load("https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/alpine_pizza_32132_16x9.jpg").into(homeIvMeal)

        homeIvMeal.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect((view!!.height + 60),  view!!.width, view!!.width, (view.height + 60), 60f)
            }
        }
        homeIvMeal.clipToOutline = true
    }
}
