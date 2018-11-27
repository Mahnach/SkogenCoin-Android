package com.skogen.coin.screens.main.home_screen.fragment

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skogen.coin.App
import com.skogen.coin.R
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.screens.main.home_screen.fragment.presentation.presenter.HomePresenter
import com.skogen.coin.screens.main.home_screen.fragment.presentation.view.HomeView
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_menu_category.view.*

class HomeFragment : BaseFragment<MainActivity, HomePresenter>(), HomeView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_home


    override fun createPresenter(): HomePresenter {
        return HomePresenter(this)
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        Glide.with(context!!).load("https://cdn.newsday.com/polopoly_fs/1.10042088.1426015995!/httpImage/image.JPG_gen/derivatives/landscape_768/image.JPG").into(homeIvMeal)
        homeIvMeal.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, 80f)
            }
        }
        homeIvMeal.clipToOutline = true

        homeClDailyOffer.setOnClickListener(this)
        homeClScanQr.setOnClickListener(this)
        homeClMyMenus.setOnClickListener(this)
        homeClSearch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when(v) {
                homeClDailyOffer -> {}
                homeClScanQr -> {}
                homeClMyMenus -> {}
                homeClSearch -> {}
            }
        }
    }
}
