package com.skogen.coin.screens.login.welcome_screen.fragment

import android.os.Bundle
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.welcome_screen.fragment.presentation.presenter.WelcomePresenter
import com.skogen.coin.screens.login.welcome_screen.fragment.presentation.view.WelcomeView
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*
import timber.log.Timber

class WelcomeFragment : BaseFragment<LoginActivity, WelcomePresenter>(), WelcomeView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_welcome

    override fun createPresenter(): WelcomePresenter {
        return WelcomePresenter(this)
    }

    companion object {
        fun newInstance(): WelcomeFragment {
            val args = Bundle()
            val fragment = WelcomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        welcomeBtnRegister.setOnClickListener(this)
        welcomeBtnLogin.setOnClickListener(this)
        welcomeTvBedrift.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when(v){
                welcomeBtnRegister -> Timber.e("welcomeBtnRegister -> ")
                welcomeBtnLogin -> Timber.e("welcomeBtnLogin -> ")
                welcomeTvBedrift -> Timber.e("welcomeTvBedrift -> ")
            }
        }
    }
}
