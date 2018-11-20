package com.skogen.coin.screens.login

import com.skogen.coin.screens.login.presentation.view.LoginView
import com.skogen.coin.screens.login.presentation.presenter.LoginPresenter
import com.skogen.coin.R
import com.skogen.coin.screens.login.welcome_screen.fragment.WelcomeFragment
import com.skogen.coin.skeleton.activity.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun createPresenter(): LoginPresenter = LoginPresenter(this)

    override fun initViews() {
        replaceFragment(R.id.loginContainer, WelcomeFragment.newInstance(), null)
    }
}
