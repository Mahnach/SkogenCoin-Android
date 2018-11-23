package com.skogen.coin.screens.main.menu_screen.fragment.presentation.view

import com.skogen.coin.models.MenuMealModel
import com.skogen.coin.skeleton.presentation.BaseView

interface MenuView : BaseView {

    fun setMenuItems(menuList: ArrayList<MenuMealModel>)
    fun setEmptyState()

}
