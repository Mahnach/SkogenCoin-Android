package com.skogen.coin.screens.login.login_screen.fragment.presentation.presenter

import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.login_screen.fragment.presentation.view.LoginPinView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.vicpin.krealmextensions.queryFirst

class LoginPinPresenter(override val view: LoginPinView) : BasePresenter() {

    fun init() {}

    fun checkPinIsCorrect(pin: String) {
        val userModel = UserModel().queryFirst()
        if (userModel?.pin!!.contentEquals(pin))
            view.showOkResult()
        else view.showErrorPin()
    }
}
