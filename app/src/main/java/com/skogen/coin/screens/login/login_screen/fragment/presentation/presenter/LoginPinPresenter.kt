package com.skogen.coin.screens.login.login_screen.fragment.presentation.presenter

import com.skogen.coin.screens.login.login_screen.fragment.presentation.view.LoginPinView
import com.skogen.coin.skeleton.presentation.BasePresenter
import timber.log.Timber

class LoginPinPresenter(override val view: LoginPinView) : BasePresenter() {

    fun init() {}

    fun checkPinIsCorrect(phoneNumber: String?, pin: String) {
//        val userModel = UserModel().queryFirst()
//        if (userModel?.pin!!.contentEquals(pin))
        Timber.e("Phone $phoneNumber")
            view.showOkResult()
//        else view.showErrorPin()
    }
}
