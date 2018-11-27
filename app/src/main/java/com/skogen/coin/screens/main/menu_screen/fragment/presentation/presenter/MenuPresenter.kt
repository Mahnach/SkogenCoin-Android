package com.skogen.coin.screens.main.menu_screen.fragment.presentation.presenter

import com.pixplicity.easyprefs.library.Prefs
import com.skogen.coin.api.DefaultCallback
import com.skogen.coin.api.RetrofitService
import com.skogen.coin.models.MenuMealModel
import com.skogen.coin.screens.main.menu_screen.fragment.presentation.view.MenuView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.skogen.coin.utils.PrefsConsts
import retrofit2.Response

class MenuPresenter(override val view: MenuView) : BasePresenter() {

    fun init() {}

    fun getMenu() {
        RetrofitService.getService().getMealsList("Bearer: " + Prefs.getString(PrefsConsts.USER_TOKEN, "")).enqueue(object: DefaultCallback<ArrayList<MenuMealModel>>() {
            override fun onResponse(response: Response<ArrayList<MenuMealModel>>?) {
                if (response != null && response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty())
                    view.setMenuItems(response.body()!!)
                else
                    view.setEmptyState()
                view.hideProgressView()
            }
        })
    }
}
