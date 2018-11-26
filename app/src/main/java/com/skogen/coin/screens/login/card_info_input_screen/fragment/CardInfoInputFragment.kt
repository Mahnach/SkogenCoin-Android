package com.skogen.coin.screens.login.card_info_input_screen.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.card_info_input_screen.fragment.presentation.presenter.CardInfoInputPresenter
import com.skogen.coin.screens.login.card_info_input_screen.fragment.presentation.view.CardInfoInputView
import com.skogen.coin.skeleton.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_card_info_input.*

class CardInfoInputFragment : BaseFragment<LoginActivity, CardInfoInputPresenter>(), CardInfoInputView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_card_info_input

    override fun createPresenter(): CardInfoInputPresenter {
        return CardInfoInputPresenter(this)
    }

    companion object {
        fun newInstance(): CardInfoInputFragment {
            val args = Bundle()
            val fragment = CardInfoInputFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        cardInfoIvCamera.setOnClickListener(this)
        cardInfoBtn.setOnClickListener(this)
        initEts()
    }

    private fun initEts() {
        val cardNumberListener = MaskedTextChangedListener.installOn(
                cardInfoEtCardNumber,
                "[0000] [0000] [0000] [0000]",
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        checkBtnEnabling()
                    }
                })
        val expDateListener = MaskedTextChangedListener.installOn(
                cardInfoEtExpDate,
                "[00]/[00]",
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        checkBtnEnabling()
                    }
                })
        cardInfoEtCVC.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkBtnEnabling()
            }
        })

        cardInfoEtCardHolder.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkBtnEnabling()
            }
        })
    }

    private fun checkBtnEnabling() {
        if (!cardInfoEtCardNumber.text.isNullOrEmpty() && cardInfoEtCardNumber.text.toString().length >= 19 &&
                !cardInfoEtExpDate.text.isNullOrEmpty() && cardInfoEtExpDate.text.toString().length >= 5 &&
                !cardInfoEtCVC.text.isNullOrEmpty() && cardInfoEtCVC.text.toString().length >= 3 &&
                !cardInfoEtCardHolder.text.isNullOrEmpty()) {
            cardInfoTvRight.visibility = View.VISIBLE
            cardInfoBtn.isEnabled = true
        }
        else {
            cardInfoTvRight.visibility = View.GONE
            cardInfoBtn.isEnabled = false
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when(v) {
                cardInfoIvCamera -> {}
                cardInfoBtn -> {}
            }
        }
    }
}
