package com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.presenter

import android.net.Uri
import com.pixplicity.easyprefs.library.Prefs
import com.skogen.coin.api.DefaultCallback
import com.skogen.coin.api.RetrofitService
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.view.ExtraInfoView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.skogen.coin.utils.PrefsConsts
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import okhttp3.MediaType
import retrofit2.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ExtraInfoPresenter(override val view: ExtraInfoView) : BasePresenter() {

    fun init() {}

    fun saveUser(phone: String, email: String, photo: File?, pin: String) {
        view.showProgressView()
        var user: UserModel = UserModel().queryFirst()!!
        user.phone = phone
        user.email = email
//        user.photo = photo
        user.password = pin
        user.save()

        val name = MultipartBody.Part.createFormData("name", user.name)
        val surname = MultipartBody.Part.createFormData("surname", user.surname)
        val phone = MultipartBody.Part.createFormData("phone", user.phone!!)
        val email = MultipartBody.Part.createFormData("email", user.email)
        val password = MultipartBody.Part.createFormData("password", user.password!!)

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        var body: MultipartBody.Part? = null
        if (photo != null) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photo)
            body = MultipartBody.Part.createFormData("photo", photo?.name, requestFile)
        }

        RetrofitService.getService().performReg(name, surname, phone, email, password, body).enqueue(object : DefaultCallback<UserModel>(view) {
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
