package com.skogen.coin.screens.login.add_payment_screen.fragment

import android.os.Bundle
import android.content.Intent
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.add_payment_screen.fragment.presentation.presenter.AddPaymentPresenter
import com.skogen.coin.screens.login.add_payment_screen.fragment.presentation.view.AddPaymentView
import com.skogen.coin.screens.login.scan_card_screen.fragment.ScanCardFragment
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_payment.*

class AddPaymentFragment : BaseFragment<LoginActivity, AddPaymentPresenter>(), AddPaymentView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_add_payment

    override fun createPresenter(): AddPaymentPresenter {
        return AddPaymentPresenter(this)
    }

    companion object {
        fun newInstance(): AddPaymentFragment {
            val args = Bundle()
            val fragment = AddPaymentFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        addPaymentBtnAddCard.setOnClickListener(this)
        addPaymentTvLater.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v) {
                addPaymentBtnAddCard -> addFragment(R.id.loginContainer, ScanCardFragment.newInstance(), null)
                addPaymentTvLater -> {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }
            }
        }
    }
}