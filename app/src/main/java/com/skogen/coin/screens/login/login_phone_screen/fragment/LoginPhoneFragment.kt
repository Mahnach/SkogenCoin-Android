package com.skogen.coin.screens.login.login_phone_screen.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.login_phone_screen.fragment.presentation.presenter.LoginPhonePresenter
import com.skogen.coin.screens.login.login_phone_screen.fragment.presentation.view.LoginPhoneView
import com.skogen.coin.screens.login.login_screen.fragment.LoginPinFragment
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.skogen.coin.utils.Validator
import kotlinx.android.synthetic.main.fragment_extra_info.*
import kotlinx.android.synthetic.main.fragment_login_phone.*

class LoginPhoneFragment : BaseFragment<LoginActivity, LoginPhonePresenter>(), LoginPhoneView {

    override val layoutId: Int
        get() = R.layout.fragment_login_phone


    override fun createPresenter(): LoginPhonePresenter {
        return LoginPhonePresenter(this)
    }

    companion object {
        fun newInstance(): LoginPhoneFragment {
            val args = Bundle()
            val fragment = LoginPhoneFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        loginPhoneEtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginPhoneBtn.isEnabled = if (!text.toString().isNullOrEmpty()) {
                    Validator.phoneValidator(text.toString())
                } else
                    false
            }
        })
        loginPhoneBtn.setOnClickListener {
            addFragment(R.id.loginContainer, LoginPinFragment.newInstance(loginPhoneEtPhone.text.toString()), null)
        }

    }
}
