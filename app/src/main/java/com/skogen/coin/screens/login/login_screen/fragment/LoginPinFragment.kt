package com.skogen.coin.screens.login.login_screen.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.login_screen.fragment.presentation.presenter.LoginPinPresenter
import com.skogen.coin.screens.login.login_screen.fragment.presentation.view.LoginPinView
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_login_pin.*

class LoginPinFragment : BaseFragment<LoginActivity, LoginPinPresenter>(), LoginPinView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_login_pin

    override fun createPresenter(): LoginPinPresenter {
        return LoginPinPresenter(this)
    }

    companion object {
        const val ARG_PHONE_NUMBER = "arg_phone_number"
        fun newInstance(phoneNumber: String): LoginPinFragment {
            val args = Bundle()
            val fragment = LoginPinFragment()
            args.putString(ARG_PHONE_NUMBER, phoneNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        loginPinIvBack.setOnClickListener(this)
        loginPinBtn.setOnClickListener(this)
        loginPin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginPinClBtn.visibility = if (!loginPin.text.toString().isNullOrEmpty()
                        && loginPin.text.toString().length >= 4)
                    View.VISIBLE
                else
                    View.GONE
            }
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v) {
                loginPinIvBack -> activity.onBackPressed()
                loginPinBtn -> presenter?.checkPinIsCorrect(arguments?.getString(ARG_PHONE_NUMBER), loginPin.text.toString())
                else -> Unit
            }
        }
    }

    override fun showOkResult() {
        activity.finish()
        startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun showErrorPin() {
        Toast.makeText(context, getString(R.string.error_pin_incorrect), Toast.LENGTH_SHORT).show()
    }
}
