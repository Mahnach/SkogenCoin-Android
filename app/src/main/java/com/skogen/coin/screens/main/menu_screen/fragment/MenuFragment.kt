package com.skogen.coin.screens.main.menu_screen.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.models.MenuMealModel
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.screens.main.menu_screen.fragment.presentation.adapters.MenuCategoryAdapter
import com.skogen.coin.screens.main.menu_screen.fragment.presentation.presenter.MenuPresenter
import com.skogen.coin.screens.main.menu_screen.fragment.presentation.view.MenuView
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : BaseFragment<MainActivity, MenuPresenter>(), MenuView {

    override val layoutId: Int
        get() = R.layout.fragment_menu

    override fun createPresenter(): MenuPresenter {
        return MenuPresenter(this)
    }

    companion object {
        fun newInstance(): MenuFragment {
            val args = Bundle()
            val fragment = MenuFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    lateinit var rvAdapter: MenuCategoryAdapter

    override fun initViews(rootView: View?) {
        rvAdapter = MenuCategoryAdapter(context)
        menuRv.layoutManager = LinearLayoutManager(context)
        menuRv.adapter = rvAdapter
        presenter?.getMenu()
    }

    override fun setMenuItems(menuList: ArrayList<MenuMealModel>) {
        rvAdapter.setItems(menuList)
    }

    override fun setEmptyState() {
    }

}
