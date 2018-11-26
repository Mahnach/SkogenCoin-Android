package com.skogen.coin.screens.login.login_screen.fragment.presentation.presenter

import com.pixplicity.easyprefs.library.Prefs
import com.skogen.coin.api.DefaultCallback
import com.skogen.coin.api.RetrofitService
import com.skogen.coin.api.requests.LoginRequest
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.login_screen.fragment.presentation.view.LoginPinView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.skogen.coin.utils.PrefsConsts
import com.vicpin.krealmextensions.save
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginPinPresenter(override val view: LoginPinView) : BasePresenter() {

    fun init() {}

    fun checkPinIsCorrect(phoneNumber: String?, pin: String) {
        view.showProgressView()
        RetrofitService.getService().performLogin(LoginRequest(phoneNumber, pin)).enqueue(object : DefaultCallback<UserModel>(view) {
            override fun onResponse(response: Response<UserModel>?) {
                Timber.e("onResponse")
                view.hideProgressView()
                if (response != null && response.isSuccessful && response.body() != null) {
                    response.body()!!.save()
                    Prefs.putString(PrefsConsts.USER_TOKEN, response.body()!!.token)
                    view.showOkResult()
                } else {
                    view.showErrorPin()
                }
            }
        })
    }
}
