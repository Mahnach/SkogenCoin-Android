package com.skogen.coin.screens.login.scan_card_screen.fragment

import android.os.Bundle
import android.view.View
import com.skogen.coin.R
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.card_info_input_screen.fragment.CardInfoInputFragment
import com.skogen.coin.screens.login.scan_card_screen.fragment.presentation.presenter.ScanCardPresenter
import com.skogen.coin.screens.login.scan_card_screen.fragment.presentation.view.ScanCardView
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.fragment_scan_card.*

class ScanCardFragment : BaseFragment<LoginActivity, ScanCardPresenter>(), ScanCardView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_scan_card

    override fun createPresenter(): ScanCardPresenter {
        return ScanCardPresenter(this)
    }

    companion object {
        fun newInstance(): ScanCardFragment {
            val args = Bundle()
            val fragment = ScanCardFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        val user = UserModel().queryFirst()
        scanCardTvUser.text = getString(R.string.name_placeholder, user?.name, user?.surname)
        scanCardIvCamera.setOnClickListener(this)
        scanCardBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when(v) {
                scanCardIvCamera -> {}
                scanCardBtn -> addFragment(R.id.loginContainer, CardInfoInputFragment.newInstance(), null)
            }
        }
    }
}
