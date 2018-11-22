package com.skogen.coin.screens.login.username_screen.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.extrainfo_screen.fragment.ExtraInfoFragment
import com.skogen.coin.screens.login.username_screen.fragment.presentation.presenter.UsernamePresenter
import com.skogen.coin.screens.login.username_screen.fragment.presentation.view.UsernameView
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.vicpin.krealmextensions.create
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.fragment_username.*
import timber.log.Timber

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
        userNameEtName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!text.isNullOrEmpty() && !userNameEtSurname.text.toString().isNullOrEmpty()) {
                    usernameTvConfirm.visibility = View.VISIBLE
                    userNameBtn.isEnabled = true
                }
                else {
                    usernameTvConfirm.visibility = View.GONE
                    userNameBtn.isEnabled = false
                }
            }
        })

        userNameEtSurname.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!text.isNullOrEmpty() && !userNameEtName.text.toString().isNullOrEmpty()) {
                    usernameTvConfirm.visibility = View.VISIBLE
                    userNameBtn.isEnabled = true
                }
                else {
                    usernameTvConfirm.visibility = View.GONE
                    userNameBtn.isEnabled = false
                }
            }
        })

        userNameBtn.setOnClickListener {
            UserModel().deleteAll()
            UserModel(name = userNameEtName.text.toString(), surname = userNameEtSurname.text.toString()).createOrUpdate()
            addFragment(R.id.loginContainer, ExtraInfoFragment.newInstance(), null)
        }
    }
}
