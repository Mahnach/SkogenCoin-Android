package com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.presenter

import com.pixplicity.easyprefs.library.Prefs
import com.skogen.coin.api.DefaultCallback
import com.skogen.coin.api.RetrofitService
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.view.ExtraInfoView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.skogen.coin.utils.PrefsConsts
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import okhttp3.MultipartBody



class ExtraInfoPresenter(override val view: ExtraInfoView) : BasePresenter() {

    fun init() {}

    fun saveUser(phone: String, email: String, photo: String, pin: String) {
        view.showProgressView()
        var user: UserModel = UserModel().queryFirst()!!
        user.phone = phone
        user.email = email
        user.photo = photo
        user.password = pin
        user.save()

        val name = MultipartBody.Part.createFormData("name", user.name)
        val surname = MultipartBody.Part.createFormData("surname", user.surname)
        val phone = MultipartBody.Part.createFormData("phone", user.phone!!)
        val email = MultipartBody.Part.createFormData("email", user.email)
        val password = MultipartBody.Part.createFormData("password", user.password!!)

        RetrofitService.getService().performReg(name, surname, phone, email, password).enqueue(object : DefaultCallback<UserModel>(view) {
            override fun onResponse(response: Response<UserModel>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    var userModel = response.body()!!
                    userModel.save()
                    Prefs.putString(PrefsConsts.USER_TOKEN, userModel.token)
                    view.showOkResult()
                }
            }
        })
    }
}
