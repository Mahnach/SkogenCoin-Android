package com.skogen.coin.screens.login.username_screen.fragment

import android.os.Bundle
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.username_screen.fragment.presentation.presenter.UsernamePresenter
import com.skogen.coin.screens.login.username_screen.fragment.presentation.view.UsernameView
import com.skogen.coin.skeleton.fragment.BaseFragment

class UsernameFragment : BaseFragment<LoginActivity, UsernamePresenter>(), UsernameView {

    override val layoutId: Int
        get() = R.layout.fragment_username

    override fun createPresenter(): UsernamePresenter {
        return UsernamePresenter(this)
    }

    companion object {
        fun newInstance(): UsernameFragment {
            val args = Bundle()
            val fragment = UsernameFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {

    }
}
