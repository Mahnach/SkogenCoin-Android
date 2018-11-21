package com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.presenter

import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.view.ExtraInfoView
import com.skogen.coin.skeleton.presentation.BasePresenter

class ExtraInfoPresenter(override val view: ExtraInfoView) : BasePresenter() {

    fun init() {}


    fun saveUser(phone: String, email: String, photo: String, pin: String) {
        view.showOkResult()
    }
}
