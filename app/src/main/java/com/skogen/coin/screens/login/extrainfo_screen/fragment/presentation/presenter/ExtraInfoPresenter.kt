package com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.presenter

import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.view.ExtraInfoView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import timber.log.Timber

class ExtraInfoPresenter(override val view: ExtraInfoView) : BasePresenter() {

    fun init() {}

    fun saveUser(phone: String, email: String, photo: String, pin: String) {
        var user: UserModel = UserModel().queryFirst()!!
        user.phone = phone
        user.email = email
        user.photo = photo
        user.pin = pin
        user.save()

        Timber.e("${UserModel().queryFirst()?.phone} ${UserModel().queryFirst()?.name}")
        view.showOkResult()
    }
}
